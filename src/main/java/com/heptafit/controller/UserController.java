package com.heptafit.controller;

import com.heptafit.model.User;
import com.heptafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }
    
    @GetMapping("/{id}/metrics")
    public ResponseEntity<UserMetrics> getUserMetrics(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new UserMetrics(
                    user.getBmi(),
                    user.getDailyCalorieNeeds(),
                    user.getProteinGoal(),
                    user.getCarbsGoal(),
                    user.getFatsGoal()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}

record UserMetrics(
    double bmi,
    int dailyCalorieNeeds,
    int proteinGoal,
    int carbsGoal,
    int fatsGoal
) {} 