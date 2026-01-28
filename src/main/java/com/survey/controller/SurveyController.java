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
    public ResponseEntity<?> getQuestions(@PathVariable String role) {
        // Validate role
        List<String> validRoles = List.of("supplier", "production", "venue", "planner");
        if (!validRoles.contains(role.toLowerCase())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid role. Must be one of: supplier, production, venue, planner");
            return ResponseEntity.badRequest().body(error);
        }
        
        List<Question> questions = surveyService.getQuestionsByRole(role);
        return ResponseEntity.ok(questions);
    }
    
    @PostMapping("/response")
    public ResponseEntity<?> submitResponse(@RequestBody Map<String, Object> request) {
        // Validate required fields
        if (request.get("respondentName") == null || 
            ((String) request.get("respondentName")).trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Respondent name is required");
            return ResponseEntity.badRequest().body(error);
        }
        
        if (request.get("role") == null || request.get("questionId") == null || 
            request.get("optionIds") == null || request.get("sessionId") == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Missing required fields");
            return ResponseEntity.badRequest().body(error);
        }
        
        String respondentName = ((String) request.get("respondentName")).trim();
        String role = (String) request.get("role");
        
        // Validate role
        List<String> validRoles = List.of("supplier", "production", "venue", "planner");
        if (!validRoles.contains(role.toLowerCase())) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid role");
            return ResponseEntity.badRequest().body(error);
        }
        
        try {
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
        } catch (NumberFormatException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid question or option ID format");
            return ResponseEntity.badRequest().body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
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
