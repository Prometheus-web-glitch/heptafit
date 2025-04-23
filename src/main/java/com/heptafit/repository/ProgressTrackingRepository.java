package com.heptafit.repository;

import com.heptafit.model.ProgressTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ProgressTrackingRepository extends JpaRepository<ProgressTracking, Long> {
    List<ProgressTracking> findByUserId(Long userId);
    List<ProgressTracking> findByDateBetween(LocalDate start, LocalDate end);
    List<ProgressTracking> findByUserIdOrderByDateDesc(Long userId);
} 