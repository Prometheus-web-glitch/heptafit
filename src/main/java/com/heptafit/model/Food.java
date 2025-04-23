package com.heptafit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "foods")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;
    private String servingSize;
    private String brand;

    @ManyToMany(mappedBy = "foods")
    private Set<Meal> meals = new HashSet<>();

    public Food() {
    }

    public Food(String name, String description, int calories, int protein, int carbs, int fats) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }
} 