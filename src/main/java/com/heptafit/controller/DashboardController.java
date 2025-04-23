package com.heptafit.controller;

import com.heptafit.model.User;
import com.heptafit.service.UserService;
import com.heptafit.service.PlanGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

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

    @GetMapping("/workouts")
    public String workouts(Model model) {
        User user = userService.getMostRecentUser()
                .orElseThrow(() -> new RuntimeException("No users found"));
        model.addAttribute("workoutPlan", planGeneratorService.generateWorkoutPlan(user));
        model.addAttribute("user", user);
        return "workouts";
    }

    @GetMapping("/nutrition")
    public String nutrition(Model model) {
        User user = userService.getMostRecentUser()
                .orElseThrow(() -> new RuntimeException("No users found"));
        model.addAttribute("nutritionPlan", planGeneratorService.generateNutritionPlan(user));
        model.addAttribute("user", user);
        return "nutrition";
    }

    @GetMapping("/mental-wellness")
    public String mentalWellness(Model model) {
        User user = userService.getMostRecentUser()
                .orElseThrow(() -> new RuntimeException("No users found"));
        Map<String, Object> wellnessPlan = planGeneratorService.generateWellnessPlan(user);
        model.addAttribute("mentalWellness", wellnessPlan.get("mentalWellness"));
        model.addAttribute("user", user);
        return "mental-wellness";
    }

    @GetMapping("/sleep")
    public String sleep(Model model) {
        User user = userService.getMostRecentUser()
                .orElseThrow(() -> new RuntimeException("No users found"));
        Map<String, Object> wellnessPlan = planGeneratorService.generateWellnessPlan(user);
        model.addAttribute("sleep", wellnessPlan.get("sleep"));
        model.addAttribute("user", user);
        return "sleep";
    }
}
