let selectedRole = '';
let respondentName = '';
let sessionId = '';
let questions = [];
let currentQuestionIndex = 0;
let responses = {};

// API Base URL
const API_BASE = '/api/survey';

// Role Selection
function selectRole(role) {
    selectedRole = role;
    document.getElementById('role-selection').classList.add('hidden');
    document.getElementById('respondent-info').classList.remove('hidden');
}

// Start Survey
async function startSurvey() {
    respondentName = document.getElementById('respondent-name').value.trim();
    
    if (!respondentName) {
        alert('Please enter your name');
        return;
    }
    
    // Create session
    try {
        const sessionResponse = await fetch(`${API_BASE}/session`);
        const sessionData = await sessionResponse.json();
        sessionId = sessionData.sessionId;
        
        // Load questions
        const questionsResponse = await fetch(`${API_BASE}/questions/${selectedRole}`);
        questions = await questionsResponse.json();
        
        if (questions.length === 0) {
            alert('No questions available for this role');
            return;
        }
        
        // Initialize responses
        responses = {};
        currentQuestionIndex = 0;
        
        // Show survey
        document.getElementById('respondent-info').classList.add('hidden');
        document.getElementById('survey-section').classList.remove('hidden');
        
        displayQuestion();
    } catch (error) {
        console.error('Error starting survey:', error);
        alert('Error loading survey. Please try again.');
    }
}

// Display Current Question
function displayQuestion() {
    const question = questions[currentQuestionIndex];
    const container = document.getElementById('question-container');
    
    // Update progress bar
    const progress = ((currentQuestionIndex + 1) / questions.length) * 100;
    document.getElementById('progress').style.width = progress + '%';
    
    // Build question HTML
    let optionsHTML = '';
    const inputType = question.type === 'single-select' ? 'radio' : 'checkbox';
    
    question.options.forEach(option => {
        const isChecked = responses[question.id] && responses[question.id].includes(option.id);
        optionsHTML += `
            <div class="option ${isChecked ? 'selected' : ''}" onclick="selectOption(${question.id}, ${option.id}, '${inputType}')">
                <input type="${inputType}" 
                       name="question-${question.id}" 
                       value="${option.id}" 
                       id="option-${option.id}"
                       ${isChecked ? 'checked' : ''}>
                <label for="option-${option.id}">${option.text}</label>
            </div>
        `;
    });
    
    container.innerHTML = `
        <div class="question-card">
            <div class="question-number">Question ${currentQuestionIndex + 1} of ${questions.length}</div>
            <div class="question-text">${question.text}</div>
            <div class="options">
                ${optionsHTML}
            </div>
        </div>
    `;
    
    // Update navigation buttons
    document.getElementById('prev-btn').disabled = currentQuestionIndex === 0;
    
    if (currentQuestionIndex === questions.length - 1) {
        document.getElementById('next-btn').classList.add('hidden');
        document.getElementById('submit-btn').classList.remove('hidden');
    } else {
        document.getElementById('next-btn').classList.remove('hidden');
        document.getElementById('submit-btn').classList.add('hidden');
    }
}

// Select Option
function selectOption(questionId, optionId, inputType) {
    if (inputType === 'radio') {
        responses[questionId] = [optionId];
        // Update UI
        document.querySelectorAll(`input[name="question-${questionId}"]`).forEach(input => {
            const option = input.closest('.option');
            if (parseInt(input.value) === optionId) {
                input.checked = true;
                option.classList.add('selected');
            } else {
                input.checked = false;
                option.classList.remove('selected');
            }
        });
    } else {
        // Checkbox (multi-select)
        if (!responses[questionId]) {
            responses[questionId] = [];
        }
        
        const checkbox = document.getElementById(`option-${optionId}`);
        const option = checkbox.closest('.option');
        
        if (responses[questionId].includes(optionId)) {
            responses[questionId] = responses[questionId].filter(id => id !== optionId);
            checkbox.checked = false;
            option.classList.remove('selected');
        } else {
            responses[questionId].push(optionId);
            checkbox.checked = true;
            option.classList.add('selected');
        }
    }
}

// Previous Question
function previousQuestion() {
    if (currentQuestionIndex > 0) {
        currentQuestionIndex--;
        displayQuestion();
    }
}

// Next Question
function nextQuestion() {
    const currentQuestion = questions[currentQuestionIndex];
    
    // Validate response
    if (!responses[currentQuestion.id] || responses[currentQuestion.id].length === 0) {
        alert('Please select an answer before proceeding');
        return;
    }
    
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        displayQuestion();
    }
}

// Submit Survey
async function submitSurvey() {
    const currentQuestion = questions[currentQuestionIndex];
    
    // Validate last question
    if (!responses[currentQuestion.id] || responses[currentQuestion.id].length === 0) {
        alert('Please select an answer before submitting');
        return;
    }
    
    // Submit all responses
    try {
        for (const [questionId, optionIds] of Object.entries(responses)) {
            const payload = {
                respondentName: respondentName,
                role: selectedRole,
                questionId: parseInt(questionId),
                optionIds: optionIds,
                sessionId: sessionId
            };
            
            await fetch(`${API_BASE}/response`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });
        }
        
        // Show completion message
        document.getElementById('survey-section').classList.add('hidden');
        document.getElementById('completion-section').classList.remove('hidden');
        
    } catch (error) {
        console.error('Error submitting survey:', error);
        alert('Error submitting survey. Please try again.');
    }
}

// Reset Survey
function resetSurvey() {
    selectedRole = '';
    respondentName = '';
    sessionId = '';
    questions = [];
    currentQuestionIndex = 0;
    responses = {};
    
    document.getElementById('completion-section').classList.add('hidden');
    document.getElementById('role-selection').classList.remove('hidden');
    document.getElementById('respondent-name').value = '';
}
