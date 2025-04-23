package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // "WORKOUT", "MEAL", "MEDITATION", "SLEEP"
    private String title;
    private String description;
    private String content;
    private LocalDateTime generatedAt;
    private boolean isRead;
    private int priority; // 1-5 scale
    private String category;
    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Recommendation() {
    }

    public Recommendation(String type, String title, String description, String content) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.content = content;
        this.generatedAt = LocalDateTime.now();
        this.isRead = false;
        this.priority = 3; // Default priority
    }
} 