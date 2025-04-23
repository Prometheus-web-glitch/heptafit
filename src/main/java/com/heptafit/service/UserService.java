package com.heptafit.service;

import com.heptafit.model.User;
import com.heptafit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(User user) {
        // Basic validation
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // Calculate metrics
        user.calculateBmi();
        user.calculateDailyCalorieNeeds();
        user.calculateMacronutrientGoals();
        
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User updateUserProfile(Long userId, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Update basic information
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            
            // Update health metrics
            user.setHeight(updatedUser.getHeight());
            user.setWeight(updatedUser.getWeight());
            user.setTargetWeight(updatedUser.getTargetWeight());
            user.setWeightGoal(updatedUser.getWeightGoal());
            user.setActivityLevel(updatedUser.getActivityLevel());
            user.setGender(updatedUser.getGender());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            
            // Calculate and update derived metrics
            user.calculateBmi();
            user.calculateDailyCalorieNeeds();
            user.calculateMacronutrientGoals();
            
            return userRepository.save(user);
        }
        return null;
    }
    
    public User calculateUserMetrics(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Calculate all metrics
            user.calculateBmi();
            user.calculateDailyCalorieNeeds();
            user.calculateMacronutrientGoals();
            
            return userRepository.save(user);
        }
        return null;
    }
    
    public Optional<User> getMostRecentUser() {
        return userRepository.findFirstByOrderByIdDesc();
    }
} 