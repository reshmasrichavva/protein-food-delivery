package com.proteinfood.app.model;

import java.math.BigDecimal;
import java.util.UUID;

public class FoodPackage {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;
    private String imageUrl;
    private boolean available;
    private String category;

    public FoodPackage() {
        this.id = UUID.randomUUID().toString();
    }

    public FoodPackage(String name, String description, BigDecimal price, int calories, 
                       int protein, int carbs, int fats, String imageUrl, boolean available, 
                       String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.imageUrl = imageUrl;
        this.available = available;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
