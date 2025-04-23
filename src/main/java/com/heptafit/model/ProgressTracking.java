package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "progress_tracking")
@Data
public class ProgressTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double weight;
    private Double bodyFatPercentage;
    private Double muscleMass;
    private Double waistCircumference;
    private Double hipCircumference;
    private Double chestCircumference;
    private Double armCircumference;
    private Double thighCircumference;
    private String notes;
    private String mood;
    private int energyLevel; // 1-10 scale
    private int motivationLevel; // 1-10 scale

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ProgressTracking() {
    }

    public ProgressTracking(LocalDate date, Double weight) {
        this.date = date;
        this.weight = weight;
    }
} 