package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sleep_records")
public class Sleep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime bedTime;
    private LocalDateTime wakeTime;
    private int totalSleepMinutes;
    private int sleepQuality; // 1-5 scale
    
    // Sleep Stages (in minutes)
    private int remSleep;
    private int deepSleep;
    private int lightSleep;
    
    private boolean interrupted;
    private String interruptionReason;
    
    // Environmental Factors
    private double roomTemperature;
    private int roomDarkness; // 1-5 scale
    private int noiseLevel; // 1-5 scale
    
    // Pre-sleep activities
    private boolean screenTime;
    private boolean caffeine;
    private boolean exercise;
    
    private String notes;
    
    @PrePersist
    protected void calculateTotalSleep() {
        if (bedTime != null && wakeTime != null) {
            totalSleepMinutes = (int) java.time.Duration.between(bedTime, wakeTime).toMinutes();
        }
    }
} 