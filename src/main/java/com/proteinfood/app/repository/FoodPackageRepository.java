package com.proteinfood.app.repository;

import com.proteinfood.app.model.FoodPackage;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FoodPackageRepository {
    private Map<String, FoodPackage> foodPackages = new HashMap<>();

    public FoodPackageRepository() {
        // Initialize with some sample food packages
        initSampleFoodPackages();
    }

    private void initSampleFoodPackages() {
        FoodPackage package1 = new FoodPackage(
                "High Protein Meal Plan",
                "A balanced meal plan with high protein content for muscle building",
                new BigDecimal("49.99"),
                2000, 150, 200, 70,
                "https://placehold.co/600x400?text=Protein+Meal",
                true,
                "Muscle Building"
        );

        FoodPackage package2 = new FoodPackage(
                "Weight Loss Meal Plan",
                "Low calorie, high protein meals for effective weight loss",
                new BigDecimal("45.99"),
                1500, 120, 120, 50,
                "https://placehold.co/600x400?text=Weight+Loss+Meal",
                true,
                "Weight Loss"
        );

        FoodPackage package3 = new FoodPackage(
                "Vegan Protein Package",
                "Plant-based protein meals with complete nutrition",
                new BigDecimal("52.99"),
                1800, 130, 220, 60,
                "https://placehold.co/600x400?text=Vegan+Protein",
                true,
                "Vegan"
        );

        FoodPackage package4 = new FoodPackage(
                "Keto Meal Plan",
                "High fat, low carb meals for ketogenic diet followers",
                new BigDecimal("54.99"),
                1900, 130, 30, 150,
                "https://placehold.co/600x400?text=Keto+Meal",
                true,
                "Keto"
        );

        FoodPackage package5 = new FoodPackage(
                "Post-Workout Recovery Pack",
                "Optimized for post-workout recovery and muscle repair",
                new BigDecimal("39.99"),
                1600, 140, 150, 45,
                "https://placehold.co/600x400?text=Post+Workout",
                true,
                "Recovery"
        );

        save(package1);
        save(package2);
        save(package3);
        save(package4);
        save(package5);
    }

    public FoodPackage save(FoodPackage foodPackage) {
        foodPackages.put(foodPackage.getId(), foodPackage);
        return foodPackage;
    }

    public Optional<FoodPackage> findById(String id) {
        return Optional.ofNullable(foodPackages.get(id));
    }

    public List<FoodPackage> findAll() {
        return new ArrayList<>(foodPackages.values());
    }

    public void deleteById(String id) {
        foodPackages.remove(id);
    }

    public List<FoodPackage> findByCategory(String category) {
        return foodPackages.values().stream()
                .filter(fp -> fp.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<FoodPackage> findByAvailability(boolean available) {
        return foodPackages.values().stream()
                .filter(fp -> fp.isAvailable() == available)
                .collect(Collectors.toList());
    }

    public List<FoodPackage> findByPriceLessThan(BigDecimal price) {
        return foodPackages.values().stream()
                .filter(fp -> fp.getPrice().compareTo(price) < 0)
                .collect(Collectors.toList());
    }
    
    public boolean existsById(String id) {
        return foodPackages.containsKey(id);
    }
}
