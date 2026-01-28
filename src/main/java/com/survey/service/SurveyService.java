package com.survey.service;

import com.survey.model.Question;
import com.survey.model.QuestionOption;
import com.survey.model.Response;
import com.survey.repository.QuestionOptionRepository;
import com.survey.repository.QuestionRepository;
import com.survey.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SurveyService {
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private QuestionOptionRepository optionRepository;
    
    @Autowired
    private ResponseRepository responseRepository;
    
    public List<Question> getQuestionsByRole(String role) {
        return questionRepository.findByRoleOrderByOrderIndexAsc(role);
    }
    
    @Transactional
    public void saveResponse(String respondentName, String role, Long questionId, 
                            List<Long> optionIds, String sessionId) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question not found"));
        
        for (Long optionId : optionIds) {
            QuestionOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("Option not found"));
            
            Response response = new Response(respondentName, role, question, option, sessionId);
            responseRepository.save(response);
        }
    }
    
    /**
     * Calculate weighted score for a session (0-100)
     * This calculates the overall score based on question weights
     */
    public Map<String, Double> calculateScores(String sessionId) {
        List<Response> responses = responseRepository.findBySessionId(sessionId);
        
        if (responses.isEmpty()) {
            return Collections.emptyMap();
        }
        
        // Group responses by dimension
        Map<String, List<Response>> responsesByDimension = responses.stream()
            .collect(Collectors.groupingBy(r -> r.getQuestion().getDimension()));
        
        Map<String, Double> dimensionScores = new HashMap<>();
        
        // Calculate weighted average for each dimension
        for (Map.Entry<String, List<Response>> entry : responsesByDimension.entrySet()) {
            String dimension = entry.getKey();
            List<Response> dimResponses = entry.getValue();
            
            double totalWeightedScore = 0;
            double totalWeight = 0;
            
            // Group by question to handle multi-select (average their scores)
            Map<Long, List<Response>> responsesByQuestion = dimResponses.stream()
                .collect(Collectors.groupingBy(r -> r.getQuestion().getId()));
            
            for (List<Response> questionResponses : responsesByQuestion.values()) {
                Question question = questionResponses.get(0).getQuestion();
                double weight = question.getWeight();
                
                // For multi-select, average the scores
                double avgScore = questionResponses.stream()
                    .mapToDouble(r -> r.getOption().getScore())
                    .average()
                    .orElse(0.0);
                
                totalWeightedScore += avgScore * weight;
                totalWeight += weight;
            }
            
            double dimensionScore = totalWeight > 0 ? totalWeightedScore / totalWeight : 0;
            dimensionScores.put(dimension, dimensionScore);
        }
        
        // Calculate IFI = 0.45*trust + 0.35*rework + 0.20*safety
        double trustScore = dimensionScores.getOrDefault("trust", 0.0);
        double reworkScore = dimensionScores.getOrDefault("rework", 0.0);
        double safetyScore = dimensionScores.getOrDefault("safety", 0.0);
        
        double ifiScore = 0.45 * trustScore + 0.35 * reworkScore + 0.20 * safetyScore;
        
        Map<String, Double> result = new HashMap<>();
        result.put("trust", trustScore);
        result.put("rework", reworkScore);
        result.put("safety", safetyScore);
        result.put("ifi", ifiScore);
        
        return result;
    }
    
    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
