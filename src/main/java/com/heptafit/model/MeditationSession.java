package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "meditation_sessions")
@Data
public class MeditationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private String meditationType;
    private String guidedBy;
    private int stressLevelBefore; // 1-10 scale
    private int stressLevelAfter; // 1-10 scale
    private int focusLevelBefore; // 1-10 scale
    private int focusLevelAfter; // 1-10 scale
    private String notes;
    private String moodBefore;
    private String moodAfter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public MeditationSession() {
    }

    public MeditationSession(LocalDateTime startTime, LocalDateTime endTime, String meditationType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.meditationType = meditationType;
        this.durationMinutes = (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }
} 