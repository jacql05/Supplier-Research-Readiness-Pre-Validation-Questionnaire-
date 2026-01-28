package com.survey.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String role; // supplier, production, venue, planner
    
    @Column(nullable = false, length = 1000)
    private String text;
    
    @Column(nullable = false)
    private String type; // single-select or multi-select
    
    @Column(nullable = false)
    private String dimension; // trust, rework, or safety
    
    @Column(nullable = false)
    private Double weight;
    
    @Column(nullable = false)
    private Integer orderIndex;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options = new ArrayList<>();
    
    // Constructors
    public Question() {}
    
    public Question(String role, String text, String type, String dimension, Double weight, Integer orderIndex) {
        this.role = role;
        this.text = text;
        this.type = type;
        this.dimension = dimension;
        this.weight = weight;
        this.orderIndex = orderIndex;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDimension() {
        return dimension;
    }
    
    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Integer getOrderIndex() {
        return orderIndex;
    }
    
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    public List<QuestionOption> getOptions() {
        return options;
    }
    
    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }
}
