package com.heptafit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
    })
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    private String username;
    
    @NotBlank
    @Size(max = 50)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    private String lastName;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(max = 120)
    private String password;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Transient
    private String dateOfBirthString;
    
    private String gender;
    
    private Double height;
    
    private Double weight;
    
    private Double targetWeight;
    
    private String weightGoal;
    
    private String activityLevel;
    
    private String dietaryPreferences;
    
    private String fitnessGoals;
    
    private Double bmi;
    
    private Integer dailyCalorieNeeds;
    
    private Integer proteinGoal;
    
    private Integer carbsGoal;
    
    private Integer fatsGoal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MealPlan> mealPlans = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<WorkoutPlan> workoutPlans = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<MeditationSession> meditationSessions = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<SleepRecord> sleepRecords = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ProgressTracking> progressTrackings = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Recommendation> recommendations = new HashSet<>();
    
    public User() {
    }
    
    public User(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int calculateAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    // Calculate BMI
    public void calculateBmi() {
        if (height != null && weight != null && height > 0) {
            double heightInMeters = height / 100.0;
            this.bmi = weight / (heightInMeters * heightInMeters);
        }
    }

    // Calculate daily calorie needs based on Mifflin-St Jeor Equation
    public void calculateDailyCalorieNeeds() {
        if (weight != null && height != null && dateOfBirth != null) {
            int age = calculateAge();
            double bmr;
            
            if (gender != null && gender.equalsIgnoreCase("male")) {
                bmr = 10 * weight + 6.25 * height - 5 * age + 5;
            } else {
                bmr = 10 * weight + 6.25 * height - 5 * age - 161;
            }
            
            // Adjust for activity level
            double activityMultiplier = 1.2; // Default sedentary
            if (activityLevel != null) {
                switch (activityLevel.toLowerCase()) {
                    case "lightly active":
                        activityMultiplier = 1.375;
                        break;
                    case "moderately active":
                        activityMultiplier = 1.55;
                        break;
                    case "very active":
                        activityMultiplier = 1.725;
                        break;
                    case "extra active":
                        activityMultiplier = 1.9;
                        break;
                }
            }
            
            this.dailyCalorieNeeds = (int) (bmr * activityMultiplier);
            
            // Adjust for weight goal
            if (weightGoal != null) {
                switch (weightGoal.toLowerCase()) {
                    case "lose weight":
                        this.dailyCalorieNeeds -= 500;
                        break;
                    case "gain weight":
                        this.dailyCalorieNeeds += 500;
                        break;
                }
            }
        }
    }

    // Calculate macronutrient goals based on daily calorie needs
    public void calculateMacronutrientGoals() {
        if (dailyCalorieNeeds != null) {
            // Protein: 25% of calories (4 calories per gram)
            this.proteinGoal = (int) ((dailyCalorieNeeds * 0.25) / 4);
            
            // Fats: 30% of calories (9 calories per gram)
            this.fatsGoal = (int) ((dailyCalorieNeeds * 0.30) / 9);
            
            // Carbs: 45% of calories (4 calories per gram)
            this.carbsGoal = (int) ((dailyCalorieNeeds * 0.45) / 4);
        }
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
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getDateOfBirthString() {
        return dateOfBirthString;
    }
    
    public void setDateOfBirthString(String dateOfBirthString) {
        this.dateOfBirthString = dateOfBirthString;
        if (dateOfBirthString != null) {
            this.dateOfBirth = LocalDate.parse(dateOfBirthString);
        }
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
    
    public String getWeightGoal() {
        return weightGoal;
    }
    
    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }
    
    public String getActivityLevel() {
        return activityLevel;
    }
    
    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }
    
    public String getDietaryPreferences() {
        return dietaryPreferences;
    }
    
    public void setDietaryPreferences(String dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }
    
    public String getFitnessGoals() {
        return fitnessGoals;
    }
    
    public void setFitnessGoals(String fitnessGoals) {
        this.fitnessGoals = fitnessGoals;
    }
    
    public Double getBmi() {
        return bmi;
    }
    
    public Integer getDailyCalorieNeeds() {
        return dailyCalorieNeeds;
    }
    
    public Integer getProteinGoal() {
        return proteinGoal;
    }
    
    public Integer getCarbsGoal() {
        return carbsGoal;
    }
    
    public Integer getFatsGoal() {
        return fatsGoal;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public void setDailyCalorieNeeds(Integer dailyCalorieNeeds) {
        this.dailyCalorieNeeds = dailyCalorieNeeds;
    }

    public void setProteinGoal(Integer proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public void setCarbsGoal(Integer carbsGoal) {
        this.carbsGoal = carbsGoal;
    }

    public void setFatsGoal(Integer fatsGoal) {
        this.fatsGoal = fatsGoal;
    }
} 