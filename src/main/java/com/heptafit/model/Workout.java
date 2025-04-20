package com.heptafit.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String type; // STRENGTH, CARDIO, HIIT, FLEXIBILITY
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private int caloriesBurned;
    
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Exercise> exercises;
    
    private String notes;
    private int intensityLevel; // 1-5 scale
    private boolean completed;
    
    @PrePersist
    protected void onCreate() {
        if (startTime == null) {
            startTime = LocalDateTime.now();
        }
    }
}

@Data
@Entity
@Table(name = "exercises")
class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;
    
    private String name;
    private int sets;
    private int reps;
    private double weight; // in kg
    private String notes;
    private boolean completed;
} 