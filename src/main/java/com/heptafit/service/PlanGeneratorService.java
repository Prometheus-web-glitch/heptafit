package com.heptafit.service;

import com.heptafit.model.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class PlanGeneratorService {
    
    public Map<String, Object> generateWorkoutPlan(User user) {
        Map<String, Object> workoutPlan = new HashMap<>();
        List<Map<String, Object>> weeklyWorkouts = new ArrayList<>();
        
        // Calculate workout intensity based on activity level and weight goal
        String intensity = calculateIntensity(user.getActivityLevel(), user.getWeightGoal());
        int weeklyWorkoutDays = determineWorkoutDays(user.getActivityLevel());
        
        // Generate weekly workout schedule
        for (int i = 0; i < weeklyWorkoutDays; i++) {
            Map<String, Object> workout = new HashMap<>();
            
            switch (user.getWeightGoal()) {
                case "LOSE":
                    workout.put("type", i % 2 == 0 ? "Cardio" : "Strength Training");
                    workout.put("duration", "45-60 minutes");
                    workout.put("exercises", generateWeightLossExercises(intensity));
                    break;
                    
                case "GAIN":
                    workout.put("type", i % 2 == 0 ? "Strength Training" : "Muscle Building");
                    workout.put("duration", "60-75 minutes");
                    workout.put("exercises", generateMuscleGainExercises(intensity));
                    break;
                    
                default: // MAINTAIN
                    workout.put("type", i % 3 == 0 ? "Cardio" : (i % 3 == 1 ? "Strength Training" : "Flexibility"));
                    workout.put("duration", "45-60 minutes");
                    workout.put("exercises", generateMaintenanceExercises(intensity));
                    break;
            }
            
            weeklyWorkouts.add(workout);
        }
        
        workoutPlan.put("weeklySchedule", weeklyWorkouts);
        workoutPlan.put("intensity", intensity);
        workoutPlan.put("recommendedDays", weeklyWorkoutDays);
        
        return workoutPlan;
    }
    
    public Map<String, Object> generateNutritionPlan(User user) {
        Map<String, Object> nutritionPlan = new HashMap<>();
        
        // Calculate daily caloric needs based on user metrics
        double bmr = calculateBMR(user);
        double activityMultiplier = getActivityMultiplier(user.getActivityLevel());
        double dailyCalories = bmr * activityMultiplier;
        
        // Adjust calories based on weight goal
        switch (user.getWeightGoal()) {
            case "LOSE":
                dailyCalories -= 500; // Create a 500 calorie deficit
                break;
            case "GAIN":
                dailyCalories += 500; // Create a 500 calorie surplus
                break;
        }
        
        // Calculate macronutrient ratios
        Map<String, Integer> macros = calculateMacros(dailyCalories, user.getWeightGoal());
        
        nutritionPlan.put("dailyCalories", Math.round(dailyCalories));
        nutritionPlan.put("macronutrients", macros);
        nutritionPlan.put("mealPlan", generateMealPlan(dailyCalories, user.getWeightGoal()));
        
        return nutritionPlan;
    }
    
    public Map<String, Object> generateWellnessPlan(User user) {
        Map<String, Object> wellnessPlan = new HashMap<>();
        
        // Generate sleep recommendations
        Map<String, Object> sleepPlan = new HashMap<>();
        sleepPlan.put("recommendedHours", calculateRecommendedSleep(getAge(user.getDateOfBirth())));
        sleepPlan.put("bedtimeRoutine", generateBedtimeRoutine());
        
        // Generate mental wellness recommendations
        Map<String, Object> mentalWellnessPlan = new HashMap<>();
        mentalWellnessPlan.put("dailyActivities", generateMentalWellnessActivities());
        mentalWellnessPlan.put("stressManagement", generateStressManagementTechniques());
        
        wellnessPlan.put("sleep", sleepPlan);
        wellnessPlan.put("mentalWellness", mentalWellnessPlan);
        
        return wellnessPlan;
    }
    
    private String calculateIntensity(String activityLevel, String weightGoal) {
        if (activityLevel.equals("SEDENTARY") || activityLevel.equals("LIGHT")) {
            return "Low to Moderate";
        } else if (activityLevel.equals("MODERATE")) {
            return "Moderate";
        } else {
            return "High";
        }
    }
    
    private int determineWorkoutDays(String activityLevel) {
        switch (activityLevel) {
            case "SEDENTARY":
                return 3;
            case "LIGHT":
                return 4;
            case "MODERATE":
                return 5;
            case "VERY_ACTIVE":
                return 6;
            case "EXTRA_ACTIVE":
                return 6;
            default:
                return 4;
        }
    }
    
    private List<Map<String, String>> generateWeightLossExercises(String intensity) {
        List<Map<String, String>> exercises = new ArrayList<>();
        // Add specific exercises based on intensity
        // Example exercise
        Map<String, String> exercise = new HashMap<>();
        exercise.put("name", "High-Intensity Interval Training");
        exercise.put("duration", "20 minutes");
        exercise.put("sets", "4");
        exercise.put("rest", "1 minute");
        exercises.add(exercise);
        return exercises;
    }
    
    private List<Map<String, String>> generateMuscleGainExercises(String intensity) {
        List<Map<String, String>> exercises = new ArrayList<>();
        // Add specific exercises for muscle gain
        // Example exercise
        Map<String, String> exercise = new HashMap<>();
        exercise.put("name", "Progressive Overload Training");
        exercise.put("sets", "4");
        exercise.put("reps", "8-12");
        exercise.put("rest", "90 seconds");
        exercises.add(exercise);
        return exercises;
    }
    
    private List<Map<String, String>> generateMaintenanceExercises(String intensity) {
        List<Map<String, String>> exercises = new ArrayList<>();
        // Add balanced exercises for maintenance
        // Example exercise
        Map<String, String> exercise = new HashMap<>();
        exercise.put("name", "Mixed Cardio and Strength");
        exercise.put("duration", "45 minutes");
        exercise.put("intensity", intensity);
        exercises.add(exercise);
        return exercises;
    }
    
    private double calculateBMR(User user) {
        // Mifflin-St Jeor Equation
        double bmr;
        int age = getAge(user.getDateOfBirth());
        
        if (user.getGender().equals("MALE")) {
            bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * age) + 5;
        } else {
            bmr = (10 * user.getWeight()) + (6.25 * user.getHeight()) - (5 * age) - 161;
        }
        
        return bmr;
    }
    
    private double getActivityMultiplier(String activityLevel) {
        switch (activityLevel) {
            case "SEDENTARY":
                return 1.2;
            case "LIGHT":
                return 1.375;
            case "MODERATE":
                return 1.55;
            case "VERY_ACTIVE":
                return 1.725;
            case "EXTRA_ACTIVE":
                return 1.9;
            default:
                return 1.375;
        }
    }
    
    private Map<String, Integer> calculateMacros(double calories, String weightGoal) {
        Map<String, Integer> macros = new HashMap<>();
        
        switch (weightGoal) {
            case "LOSE":
                macros.put("protein", (int) (calories * 0.40 / 4)); // 40% protein
                macros.put("carbs", (int) (calories * 0.35 / 4));   // 35% carbs
                macros.put("fats", (int) (calories * 0.25 / 9));    // 25% fats
                break;
            case "GAIN":
                macros.put("protein", (int) (calories * 0.30 / 4)); // 30% protein
                macros.put("carbs", (int) (calories * 0.50 / 4));   // 50% carbs
                macros.put("fats", (int) (calories * 0.20 / 9));    // 20% fats
                break;
            default: // MAINTAIN
                macros.put("protein", (int) (calories * 0.35 / 4)); // 35% protein
                macros.put("carbs", (int) (calories * 0.45 / 4));   // 45% carbs
                macros.put("fats", (int) (calories * 0.20 / 9));    // 20% fats
        }
        
        return macros;
    }
    
    private List<Map<String, Object>> generateMealPlan(double calories, String weightGoal) {
        List<Map<String, Object>> meals = new ArrayList<>();
        // Generate meal suggestions based on calories and weight goal
        // Example meal
        Map<String, Object> meal = new HashMap<>();
        meal.put("name", "Breakfast");
        meal.put("calories", calories * 0.3);
        meal.put("suggestions", List.of(
            "Oatmeal with protein powder and fruits",
            "Greek yogurt with granola and berries",
            "Whole grain toast with eggs and avocado"
        ));
        meals.add(meal);
        return meals;
    }
    
    private int calculateRecommendedSleep(int age) {
        if (age < 18) {
            return 9;
        } else if (age < 65) {
            return 8;
        } else {
            return 7;
        }
    }
    
    private List<String> generateBedtimeRoutine() {
        return List.of(
            "Avoid screens 1 hour before bed",
            "Practice relaxation techniques",
            "Maintain a consistent sleep schedule",
            "Keep bedroom cool and dark"
        );
    }
    
    private List<String> generateMentalWellnessActivities() {
        return List.of(
            "10 minutes of morning meditation",
            "Mindful breathing exercises",
            "Daily gratitude journaling",
            "Regular social connection"
        );
    }
    
    private List<String> generateStressManagementTechniques() {
        return List.of(
            "Progressive muscle relaxation",
            "Deep breathing exercises",
            "Regular physical activity",
            "Time management strategies"
        );
    }
    
    private int getAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
} 