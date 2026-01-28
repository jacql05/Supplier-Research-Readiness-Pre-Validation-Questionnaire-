package com.survey.service;

import com.survey.model.Question;
import com.survey.model.QuestionOption;
import com.survey.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public void run(String... args) {
        // Only initialize if database is empty
        if (questionRepository.count() == 0) {
            initializeSupplierQuestions();
            initializeProductionQuestions();
            initializeVenueQuestions();
            initializePlannerQuestions();
        }
    }
    
    private void initializeSupplierQuestions() {
        createQuestion("supplier", 1, "How reliable is the supplier's delivery schedule?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Very reliable", "Somewhat reliable", "Neutral", "Somewhat unreliable", "Very unreliable"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 2, "How would you rate the quality of materials provided?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 3, "Does the supplier communicate proactively about potential delays?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 4, "How often do you need to rework materials from this supplier?", 
                      "single-select", "rework", 1.0,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 5, "What percentage of deliveries meet specifications?", 
                      "single-select", "rework", 0.9,
                      new String[]{"90-100%", "70-89%", "50-69%", "30-49%", "Below 30%"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 6, "How responsive is the supplier to quality issues?", 
                      "single-select", "rework", 0.8,
                      new String[]{"Very responsive", "Responsive", "Neutral", "Slow", "Very slow"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 7, "Does the supplier follow safety protocols in handling materials?", 
                      "single-select", "safety", 1.0,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 8, "Are materials properly packaged to prevent damage?", 
                      "single-select", "safety", 0.9,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 9, "How would you rate the supplier's documentation quality?", 
                      "single-select", "trust", 0.7,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 10, "Does the supplier provide complete traceability information?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 11, "How often are defects found in supplied materials?", 
                      "single-select", "rework", 0.7,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 12, "Does the supplier provide safety data sheets promptly?", 
                      "single-select", "safety", 0.8,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 13, "How flexible is the supplier with order changes?", 
                      "single-select", "trust", 0.6,
                      new String[]{"Very flexible", "Flexible", "Neutral", "Inflexible", "Very inflexible"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 14, "Are materials certified for required standards?", 
                      "single-select", "safety", 0.7,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("supplier", 15, "Overall satisfaction with this supplier?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Very satisfied", "Satisfied", "Neutral", "Dissatisfied", "Very dissatisfied"},
                      new double[]{100, 75, 50, 25, 0});
    }
    
    private void initializeProductionQuestions() {
        createQuestion("production", 1, "How efficient is the production process?", 
                      "single-select", "rework", 1.0,
                      new String[]{"Very efficient", "Efficient", "Average", "Inefficient", "Very inefficient"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 2, "How often do production defects occur?", 
                      "single-select", "rework", 1.0,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 3, "Are safety procedures followed on the production floor?", 
                      "single-select", "safety", 1.0,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 4, "How reliable is the production equipment?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Very reliable", "Reliable", "Average", "Unreliable", "Very unreliable"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 5, "What is the rate of first-pass quality?", 
                      "single-select", "rework", 0.9,
                      new String[]{"90-100%", "70-89%", "50-69%", "30-49%", "Below 30%"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 6, "How well-trained is the production staff?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 7, "Are production schedules consistently met?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 8, "How often is rework required?", 
                      "single-select", "rework", 0.8,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 9, "Is personal protective equipment properly used?", 
                      "single-select", "safety", 0.9,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 10, "How effective is quality control?", 
                      "single-select", "rework", 0.7,
                      new String[]{"Very effective", "Effective", "Average", "Ineffective", "Very ineffective"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 11, "Are safety incidents properly reported?", 
                      "single-select", "safety", 0.8,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 12, "How accurate is production documentation?", 
                      "single-select", "trust", 0.7,
                      new String[]{"Very accurate", "Accurate", "Average", "Inaccurate", "Very inaccurate"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 13, "Is equipment maintenance performed on schedule?", 
                      "single-select", "safety", 0.7,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 14, "How often do material shortages cause delays?", 
                      "single-select", "rework", 0.6,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("production", 15, "Overall production readiness?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
    }
    
    private void initializeVenueQuestions() {
        createQuestion("venue", 1, "How suitable is the venue location for research activities?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 2, "Are safety facilities adequate for research work?", 
                      "single-select", "safety", 1.0,
                      new String[]{"Fully adequate", "Adequate", "Acceptable", "Inadequate", "Very inadequate"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 3, "How reliable is the venue's infrastructure?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Very reliable", "Reliable", "Average", "Unreliable", "Very unreliable"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 4, "Is the venue accessible for team members?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Very accessible", "Accessible", "Moderately", "Difficult", "Very difficult"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 5, "How often are venue-related issues reported?", 
                      "single-select", "rework", 0.9,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 6, "Are emergency procedures clearly defined?", 
                      "single-select", "safety", 0.9,
                      new String[]{"Very clear", "Clear", "Acceptable", "Unclear", "Very unclear"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 7, "How adequate is the venue space for equipment?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Very adequate", "Adequate", "Acceptable", "Inadequate", "Very inadequate"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 8, "Is environmental control (temperature, humidity) sufficient?", 
                      "single-select", "rework", 0.8,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 9, "How frequently does the venue require maintenance?", 
                      "single-select", "rework", 0.7,
                      new String[]{"Rarely", "Occasionally", "Regularly", "Frequently", "Constantly"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 10, "Are safety certifications up to date?", 
                      "single-select", "safety", 0.8,
                      new String[]{"All current", "Mostly current", "Some current", "Few current", "None current"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 11, "How suitable is the venue for collaborative work?", 
                      "single-select", "trust", 0.7,
                      new String[]{"Very suitable", "Suitable", "Acceptable", "Unsuitable", "Very unsuitable"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 12, "Is there adequate security at the venue?", 
                      "single-select", "safety", 0.7,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 13, "How often do facility issues cause project delays?", 
                      "single-select", "rework", 0.6,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 14, "Are utilities (power, water, internet) reliable?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Very reliable", "Reliable", "Average", "Unreliable", "Very unreliable"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("venue", 15, "Overall venue readiness for research?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
    }
    
    private void initializePlannerQuestions() {
        createQuestion("planner", 1, "How thorough is the project planning?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Very thorough", "Thorough", "Adequate", "Limited", "Very limited"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 2, "Are project timelines realistic?", 
                      "single-select", "trust", 0.9,
                      new String[]{"Very realistic", "Realistic", "Somewhat", "Unrealistic", "Very unrealistic"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 3, "How often are project milestones met?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Always", "Usually", "Sometimes", "Rarely", "Never"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 4, "Is risk assessment comprehensive?", 
                      "single-select", "safety", 0.9,
                      new String[]{"Very comprehensive", "Comprehensive", "Adequate", "Limited", "Very limited"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 5, "How effective is resource allocation?", 
                      "single-select", "rework", 0.9,
                      new String[]{"Very effective", "Effective", "Average", "Ineffective", "Very ineffective"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 6, "Are stakeholder expectations clearly defined?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Very clear", "Clear", "Somewhat clear", "Unclear", "Very unclear"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 7, "How often do plans require major revisions?", 
                      "single-select", "rework", 0.8,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 8, "Is safety planning integrated into project plans?", 
                      "single-select", "safety", 1.0,
                      new String[]{"Fully integrated", "Well integrated", "Somewhat", "Poorly", "Not at all"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 9, "How well are dependencies identified?", 
                      "single-select", "trust", 0.8,
                      new String[]{"Very well", "Well", "Adequately", "Poorly", "Very poorly"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 10, "Are contingency plans in place?", 
                      "single-select", "rework", 0.7,
                      new String[]{"Comprehensive", "Good", "Basic", "Limited", "None"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 11, "How effective is project communication?", 
                      "single-select", "trust", 0.7,
                      new String[]{"Very effective", "Effective", "Average", "Ineffective", "Very ineffective"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 12, "Are safety protocols documented in plans?", 
                      "single-select", "safety", 0.8,
                      new String[]{"Fully documented", "Well documented", "Partially", "Poorly", "Not documented"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 13, "How often do planning errors cause rework?", 
                      "single-select", "rework", 0.6,
                      new String[]{"Never", "Rarely", "Sometimes", "Often", "Always"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 14, "Is budget planning accurate?", 
                      "single-select", "trust", 0.7,
                      new String[]{"Very accurate", "Accurate", "Somewhat", "Inaccurate", "Very inaccurate"},
                      new double[]{100, 75, 50, 25, 0});
        
        createQuestion("planner", 15, "Overall planning readiness?", 
                      "single-select", "trust", 1.0,
                      new String[]{"Excellent", "Good", "Average", "Poor", "Very poor"},
                      new double[]{100, 75, 50, 25, 0});
    }
    
    private void createQuestion(String role, int order, String text, String type, 
                               String dimension, double weight, String[] optionTexts, double[] scores) {
        Question question = new Question(role, text, type, dimension, weight, order);
        
        for (int i = 0; i < optionTexts.length; i++) {
            QuestionOption option = new QuestionOption(question, optionTexts[i], scores[i], i + 1);
            question.getOptions().add(option);
        }
        
        questionRepository.save(question);
    }
}
