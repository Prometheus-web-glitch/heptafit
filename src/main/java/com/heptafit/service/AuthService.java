package com.heptafit.service;

import com.heptafit.dto.RegisterRequest;
import com.heptafit.model.Role;
import com.heptafit.model.User;
import com.heptafit.repository.RoleRepository;
import com.heptafit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User register(RegisterRequest request) {
        logger.info("Attempting to register user: {}", request.getUsername());
        
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Username already taken: {}", request.getUsername());
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Email already in use: {}", request.getEmail());
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());
        user.setTargetWeight(request.getTargetWeight());
        user.setActivityLevel(request.getActivityLevel());
        user.setWeightGoal(request.getWeightGoal());

        // Set default role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> {
                    logger.error("ROLE_USER not found in database");
                    return new RuntimeException("Error: Role is not found. Please contact administrator.");
                });
        user.setRoles(Collections.singleton(userRole));
        logger.info("Assigned ROLE_USER to user: {}", request.getUsername());

        // Calculate initial metrics
        user.calculateBmi();
        user.calculateDailyCalorieNeeds();
        user.calculateMacronutrientGoals();

        User savedUser = userRepository.save(user);
        logger.info("Successfully registered user: {} with ID: {}", request.getUsername(), savedUser.getId());
        return savedUser;
    }
} 