package com.heptafit.repository;

import com.heptafit.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
    List<WorkoutExercise> findByWorkoutPlanId(Long workoutPlanId);
    List<WorkoutExercise> findByMuscleGroup(String muscleGroup);
    List<WorkoutExercise> findByDifficultyLevel(String difficultyLevel);
} 