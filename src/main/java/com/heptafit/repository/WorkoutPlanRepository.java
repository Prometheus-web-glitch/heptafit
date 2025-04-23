package com.heptafit.repository;

import com.heptafit.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUserId(Long userId);
    List<WorkoutPlan> findByWorkoutType(String workoutType);
    List<WorkoutPlan> findByDifficultyLevel(String difficultyLevel);
} 