package com.heptafit.controller;

import com.heptafit.model.User;
import com.heptafit.service.UserService;
import com.heptafit.service.PlanGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PlanGeneratorService planGeneratorService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get the most recently registered user
        User user = userService.getMostRecentUser()
                .orElseThrow(() -> new RuntimeException("No users found"));
        
        // Generate personalized plans
        model.addAttribute("workoutPlan", planGeneratorService.generateWorkoutPlan(user));
        model.addAttribute("nutritionPlan", planGeneratorService.generateNutritionPlan(user));
        model.addAttribute("wellnessPlan", planGeneratorService.generateWellnessPlan(user));
        model.addAttribute("user", user);
        
        return "dashboard";
    }
}
