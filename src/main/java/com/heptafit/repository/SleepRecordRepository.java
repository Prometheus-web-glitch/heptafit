package com.heptafit.repository;

import com.heptafit.model.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SleepRecordRepository extends JpaRepository<SleepRecord, Long> {
    List<SleepRecord> findByUserId(Long userId);
    List<SleepRecord> findBySleepStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<SleepRecord> findBySleepQualityGreaterThanEqual(int quality);
} 