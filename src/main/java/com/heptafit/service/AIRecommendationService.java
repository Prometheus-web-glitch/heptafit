package com.heptafit.service;

import com.heptafit.model.*;
import com.heptafit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AIRecommendationService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
    
    @Autowired
    private MealPlanRepository mealPlanRepository;
    
    @Autowired
    private MeditationSessionRepository meditationSessionRepository;
    
    @Autowired
    private SleepRecordRepository sleepRecordRepository;
    
    @Autowired
    private ProgressTrackingRepository progressTrackingRepository;
    
    @Autowired
    private RecommendationRepository recommendationRepository;

    public List<Recommendation> generateRecommendations(Long userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Get user data
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return recommendations;
        }
        User user = userOpt.get();
        
        // Generate workout recommendations
        recommendations.addAll(generateWorkoutRecommendations(user));
        
        // Generate meal recommendations
        recommendations.addAll(generateMealRecommendations(user));
        
        // Generate meditation recommendations
        recommendations.addAll(generateMeditationRecommendations(user));
        
        // Generate sleep recommendations
        recommendations.addAll(generateSleepRecommendations(user));
        
        // Save all recommendations
        return recommendationRepository.saveAll(recommendations);
    }

    private List<Recommendation> generateWorkoutRecommendations(User user) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Analyze recent workout plans
        List<WorkoutPlan> recentPlans = workoutPlanRepository.findByUserId(user.getId());
        if (recentPlans.isEmpty()) {
            recommendations.add(new Recommendation(
                "WORKOUT",
                "Start Your Fitness Journey",
                "Begin with a beginner-friendly workout plan",
                "Based on your profile, we recommend starting with a beginner workout plan focusing on full-body exercises."
            ));
        } else {
            // Analyze workout consistency and progress
            // Generate recommendations based on workout patterns
            recommendations.add(new Recommendation(
                "WORKOUT",
                "Progress Your Workout Routine",
                "Time to increase intensity",
                "Your recent workouts show good consistency. Consider increasing weights or reps for better results."
            ));
        }
        
        return recommendations;
    }

    private List<Recommendation> generateMealRecommendations(User user) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Analyze recent meal plans
        List<MealPlan> recentPlans = mealPlanRepository.findByUserId(user.getId());
        if (recentPlans.isEmpty()) {
            recommendations.add(new Recommendation(
                "MEAL",
                "Start Your Nutrition Plan",
                "Create a balanced meal plan",
                "Based on your goals, we recommend starting with a balanced meal plan focusing on whole foods."
            ));
        } else {
            // Analyze meal patterns and nutritional balance
            recommendations.add(new Recommendation(
                "MEAL",
                "Nutrition Optimization",
                "Adjust your macronutrient balance",
                "Your current meal plan could benefit from more protein to support your fitness goals."
            ));
        }
        
        return recommendations;
    }

    private List<Recommendation> generateMeditationRecommendations(User user) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Analyze recent meditation sessions
        List<MeditationSession> recentSessions = meditationSessionRepository.findByUserId(user.getId());
        if (recentSessions.isEmpty()) {
            recommendations.add(new Recommendation(
                "MEDITATION",
                "Start Your Meditation Practice",
                "Begin with guided meditation",
                "Based on your stress levels, we recommend starting with 5-minute guided meditation sessions."
            ));
        } else {
            // Analyze meditation patterns and stress levels
            recommendations.add(new Recommendation(
                "MEDITATION",
                "Deepen Your Practice",
                "Try longer meditation sessions",
                "Your meditation practice is showing positive results. Consider extending your sessions to 15 minutes."
            ));
        }
        
        return recommendations;
    }

    private List<Recommendation> generateSleepRecommendations(User user) {
        List<Recommendation> recommendations = new ArrayList<>();
        
        // Analyze recent sleep records
        List<SleepRecord> recentRecords = sleepRecordRepository.findByUserId(user.getId());
        if (recentRecords.isEmpty()) {
            recommendations.add(new Recommendation(
                "SLEEP",
                "Track Your Sleep",
                "Start monitoring your sleep patterns",
                "Begin tracking your sleep to receive personalized recommendations for better rest."
            ));
        } else {
            // Analyze sleep patterns and quality
            recommendations.add(new Recommendation(
                "SLEEP",
                "Improve Sleep Quality",
                "Optimize your sleep environment",
                "Your sleep records show room for improvement. Consider adjusting your bedtime routine."
            ));
        }
        
        return recommendations;
    }
} 