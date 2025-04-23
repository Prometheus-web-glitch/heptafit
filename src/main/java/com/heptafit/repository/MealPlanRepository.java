package com.heptafit.repository;

import com.heptafit.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserId(Long userId);
    List<MealPlan> findByStartDateBetween(LocalDate start, LocalDate end);
    List<MealPlan> findByUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Long userId, LocalDate date, LocalDate date2);
} 