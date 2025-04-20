package com.heptafit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 20)
    private String username;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(max = 120)
    private String password;
    
    @NotBlank
    @Size(max = 50)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    private String lastName;
    
    private String dateOfBirth;
    
    private String gender;
    
    private Double height;
    
    private Double weight;
    
    private Double targetWeight;
    
    private String activityLevel;
    
    private String weightGoal;
    
    @Column(name = "daily_calorie_needs")
    private int dailyCalorieNeeds;
    
    @Column(name = "protein_goal")
    private int proteinGoal;
    
    @Column(name = "carbs_goal")
    private int carbsGoal;
    
    @Column(name = "fats_goal")
    private int fatsGoal;
    
    @Column(name = "bmi")
    private double bmi;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public User() {
    }
    
    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Double getHeight() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public Double getTargetWeight() {
        return targetWeight;
    }
    
    public void setTargetWeight(Double targetWeight) {
        this.targetWeight = targetWeight;
    }
    
    public String getActivityLevel() {
        return activityLevel;
    }
    
    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }
    
    public String getWeightGoal() {
        return weightGoal;
    }
    
    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }
    
    public int getDailyCalorieNeeds() {
        return dailyCalorieNeeds;
    }
    
    public void setDailyCalorieNeeds(int dailyCalorieNeeds) {
        this.dailyCalorieNeeds = dailyCalorieNeeds;
    }
    
    public int getProteinGoal() {
        return proteinGoal;
    }
    
    public void setProteinGoal(int proteinGoal) {
        this.proteinGoal = proteinGoal;
    }
    
    public int getCarbsGoal() {
        return carbsGoal;
    }
    
    public void setCarbsGoal(int carbsGoal) {
        this.carbsGoal = carbsGoal;
    }
    
    public int getFatsGoal() {
        return fatsGoal;
    }
    
    public void setFatsGoal(int fatsGoal) {
        this.fatsGoal = fatsGoal;
    }
    
    public double getBmi() {
        return bmi;
    }
    
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
} 