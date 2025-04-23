package com.heptafit.repository;

import com.heptafit.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByUserId(Long userId);
    List<Recommendation> findByUserIdAndType(Long userId, String type);
    List<Recommendation> findByUserIdAndIsReadFalse(Long userId);
    List<Recommendation> findByUserIdOrderByPriorityDesc(Long userId);
    List<Recommendation> findByUserIdAndCategory(Long userId, String category);
} 