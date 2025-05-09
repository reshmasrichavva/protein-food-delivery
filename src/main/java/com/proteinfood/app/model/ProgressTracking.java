package com.proteinfood.app.model;

import java.time.LocalDate;
import java.util.UUID;

public class ProgressTracking {
    private String id;
    private String userId;
    private LocalDate date;
    private double weight;
    private double bodyFatPercentage;
    private double musclePercentage;
    private int caloriesConsumed;
    private int proteinConsumed;
    private String notes;

    public ProgressTracking() {
        this.id = UUID.randomUUID().toString();
        this.date = LocalDate.now();
    }

    public ProgressTracking(String userId, LocalDate date, double weight, double bodyFatPercentage,
                           double musclePercentage, int caloriesConsumed, int proteinConsumed, String notes) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
        this.musclePercentage = musclePercentage;
        this.caloriesConsumed = caloriesConsumed;
        this.proteinConsumed = proteinConsumed;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public double getMusclePercentage() {
        return musclePercentage;
    }

    public void setMusclePercentage(double musclePercentage) {
        this.musclePercentage = musclePercentage;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getProteinConsumed() {
        return proteinConsumed;
    }

    public void setProteinConsumed(int proteinConsumed) {
        this.proteinConsumed = proteinConsumed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
