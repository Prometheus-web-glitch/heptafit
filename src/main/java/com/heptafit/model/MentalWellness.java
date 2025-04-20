package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mental_wellness")
public class MentalWellness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime timestamp;
    private int moodRating; // 1-5 scale
    private int stressLevel; // 1-5 scale
    private int anxietyLevel; // 1-5 scale
    
    private String journalEntry;
    private String emotions;
    private boolean meditationCompleted;
    private int meditationMinutes;
    
    // Chatbot Interaction
    private String userQuery;
    private String botResponse;
    private String suggestedActivity;
    private boolean activityCompleted;
    
    // Daily Affirmation
    private String dailyAffirmation;
    private boolean affirmationAcknowledged;
    
    // Wellness Metrics
    private int energyLevel; // 1-5 scale
    private int focusLevel; // 1-5 scale
    private boolean practicesMindfulness;
    
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
} 