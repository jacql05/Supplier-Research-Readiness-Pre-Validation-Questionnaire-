package com.survey.controller;

import com.survey.model.Question;
import com.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {
    
    @Autowired
    private SurveyService surveyService;
    
    @GetMapping("/questions/{role}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String role) {
        List<Question> questions = surveyService.getQuestionsByRole(role);
        return ResponseEntity.ok(questions);
    }
    
    @PostMapping("/response")
    public ResponseEntity<Map<String, String>> submitResponse(@RequestBody Map<String, Object> request) {
        String respondentName = (String) request.get("respondentName");
        String role = (String) request.get("role");
        Long questionId = Long.valueOf(request.get("questionId").toString());
        
        @SuppressWarnings("unchecked")
        List<Integer> optionIdsInt = (List<Integer>) request.get("optionIds");
        List<Long> optionIds = optionIdsInt.stream()
            .map(Long::valueOf)
            .collect(java.util.stream.Collectors.toList());
        
        String sessionId = (String) request.get("sessionId");
        
        surveyService.saveResponse(respondentName, role, questionId, optionIds, sessionId);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/session")
    public ResponseEntity<Map<String, String>> createSession() {
        String sessionId = surveyService.generateSessionId();
        Map<String, String> response = new HashMap<>();
        response.put("sessionId", sessionId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        List<String> roles = List.of("supplier", "production", "venue", "planner");
        return ResponseEntity.ok(roles);
    }
}
