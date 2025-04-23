package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "sleep_records")
@Data
public class SleepRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sleepStartTime;
    private LocalDateTime sleepEndTime;
    private int sleepDurationMinutes;
    private int sleepQuality; // 1-10 scale
    private int deepSleepMinutes;
    private int lightSleepMinutes;
    private int remSleepMinutes;
    private int wakeCount;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public SleepRecord() {
    }

    public SleepRecord(LocalDateTime sleepStartTime, LocalDateTime sleepEndTime) {
        this.sleepStartTime = sleepStartTime;
        this.sleepEndTime = sleepEndTime;
        this.sleepDurationMinutes = (int) java.time.Duration.between(sleepStartTime, sleepEndTime).toMinutes();
    }
} 