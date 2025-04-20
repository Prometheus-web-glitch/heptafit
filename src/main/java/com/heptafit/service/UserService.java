package com.heptafit.service;

import com.heptafit.model.User;
import com.heptafit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User createUser(User user) {
        // Basic validation
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Calculate BMI
        double heightInMeters = user.getHeight() / 100.0;
        user.setBmi(user.getWeight() / (heightInMeters * heightInMeters));
        
        // Calculate daily calorie needs (Basic BMR * Activity Level)
        calculateDailyCalorieNeeds(user);
        
        // Calculate macronutrient goals
        calculateMacronutrientGoals(user);
        
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found");
        }
        
        // Recalculate metrics
        double heightInMeters = user.getHeight() / 100.0;
        user.setBmi(user.getWeight() / (heightInMeters * heightInMeters));
        calculateDailyCalorieNeeds(user);
        calculateMacronutrientGoals(user);
        
        return userRepository.save(user);
    }
    
    private void calculateDailyCalorieNeeds(User user) {
        // Basic BMR calculation using Mifflin-St Jeor Equation
        double bmr;
        if ("MALE".equalsIgnoreCase(user.getGender())) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * calculateAge(user.getDateOfBirth()) + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * calculateAge(user.getDateOfBirth()) - 161;
        }
        
        // Apply activity level multiplier
        double activityMultiplier = switch (user.getActivityLevel().toUpperCase()) {
            case "SEDENTARY" -> 1.2;
            case "LIGHT" -> 1.375;
            case "MODERATE" -> 1.55;
            case "VERY_ACTIVE" -> 1.725;
            case "EXTRA_ACTIVE" -> 1.9;
            default -> 1.2;
        };
        
        int totalCalories = (int) (bmr * activityMultiplier);
        
        // Adjust based on weight goal
        switch (user.getWeightGoal().toUpperCase()) {
            case "LOSE" -> totalCalories -= 500; // Create a 500 calorie deficit
            case "GAIN" -> totalCalories += 500; // Create a 500 calorie surplus
        }
        
        user.setDailyCalorieNeeds(totalCalories);
    }
    
    private void calculateMacronutrientGoals(User user) {
        int dailyCalories = user.getDailyCalorieNeeds();
        
        // Calculate protein (30% of calories)
        int proteinCalories = (int) (dailyCalories * 0.3);
        user.setProteinGoal(proteinCalories / 4); // 4 calories per gram of protein
        
        // Calculate fats (25% of calories)
        int fatCalories = (int) (dailyCalories * 0.25);
        user.setFatsGoal(fatCalories / 9); // 9 calories per gram of fat
        
        // Calculate carbs (45% of calories)
        int carbCalories = (int) (dailyCalories * 0.45);
        user.setCarbsGoal(carbCalories / 4); // 4 calories per gram of carbs
    }
    
    private int calculateAge(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return 0;
        }
        LocalDate birthDate = LocalDate.parse(dateOfBirth);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
} 