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
    // Validate role
    const validRoles = ['supplier', 'production', 'venue', 'planner'];
    if (!validRoles.includes(role)) {
        alert('Invalid role selected');
        return;
    }
    
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
    
    // Basic input sanitization - limit length and remove special characters
    if (respondentName.length > 100) {
        alert('Name is too long (maximum 100 characters)');
        return;
    }
    
    // Remove any HTML tags for basic XSS prevention
    respondentName = respondentName.replace(/<[^>]*>/g, '');
    
    if (!respondentName) {
        alert('Please enter a valid name');
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
    
    // Clear container
    container.innerHTML = '';
    
    // Create question card
    const questionCard = document.createElement('div');
    questionCard.className = 'question-card';
    
    // Question number
    const questionNumber = document.createElement('div');
    questionNumber.className = 'question-number';
    questionNumber.textContent = `Question ${currentQuestionIndex + 1} of ${questions.length}`;
    questionCard.appendChild(questionNumber);
    
    // Question text
    const questionText = document.createElement('div');
    questionText.className = 'question-text';
    questionText.textContent = question.text;
    questionCard.appendChild(questionText);
    
    // Options container
    const optionsContainer = document.createElement('div');
    optionsContainer.className = 'options';
    
    const inputType = question.type === 'single-select' ? 'radio' : 'checkbox';
    
    question.options.forEach(option => {
        const isChecked = responses[question.id] && responses[question.id].includes(option.id);
        
        const optionDiv = document.createElement('div');
        optionDiv.className = 'option' + (isChecked ? ' selected' : '');
        optionDiv.onclick = () => selectOption(question.id, option.id, inputType);
        
        const input = document.createElement('input');
        input.type = inputType;
        input.name = `question-${question.id}`;
        input.value = option.id;
        input.id = `option-${option.id}`;
        input.checked = isChecked;
        
        const label = document.createElement('label');
        label.htmlFor = `option-${option.id}`;
        label.textContent = option.text;
        
        optionDiv.appendChild(input);
        optionDiv.appendChild(label);
        optionsContainer.appendChild(optionDiv);
    });
    
    questionCard.appendChild(optionsContainer);
    container.appendChild(questionCard);
    
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
