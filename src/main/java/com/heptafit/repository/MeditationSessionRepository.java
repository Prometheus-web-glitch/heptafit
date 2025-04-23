package com.heptafit.repository;

import com.heptafit.model.MeditationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MeditationSessionRepository extends JpaRepository<MeditationSession, Long> {
    List<MeditationSession> findByUserId(Long userId);
    List<MeditationSession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    List<MeditationSession> findByMeditationType(String meditationType);
    List<MeditationSession> findByUserIdAndStressLevelAfterLessThan(Long userId, int stressLevel);
} 