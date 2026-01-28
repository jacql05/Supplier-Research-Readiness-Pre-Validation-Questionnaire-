package com.survey.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "responses")
public class Response {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String respondentName;
    
    @Column(nullable = false)
    private String role;
    
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
    
    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private QuestionOption option;
    
    @Column(nullable = false)
    private LocalDateTime submittedAt;
    
    @Column(nullable = false)
    private String sessionId; // Group responses from the same survey session
    
    // Constructors
    public Response() {
        this.submittedAt = LocalDateTime.now();
    }
    
    public Response(String respondentName, String role, Question question, QuestionOption option, String sessionId) {
        this.respondentName = respondentName;
        this.role = role;
        this.question = question;
        this.option = option;
        this.sessionId = sessionId;
        this.submittedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRespondentName() {
        return respondentName;
    }
    
    public void setRespondentName(String respondentName) {
        this.respondentName = respondentName;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public QuestionOption getOption() {
        return option;
    }
    
    public void setOption(QuestionOption option) {
        this.option = option;
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
