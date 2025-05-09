package com.proteinfood.app.service;

import com.proteinfood.app.model.FoodPackage;
import com.proteinfood.app.repository.FoodPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodPackageService {

    @Autowired
    private FoodPackageRepository foodPackageRepository;

    public FoodPackage createFoodPackage(FoodPackage foodPackage) {
        // Validate input
        if (foodPackage.getName() == null || foodPackage.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Food package name cannot be empty");
        }
        if (foodPackage.getDescription() == null || foodPackage.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Food package description cannot be empty");
        }
        if (foodPackage.getPrice() == null) {
            throw new IllegalArgumentException("Food package price cannot be empty");
        }
        
        return foodPackageRepository.save(foodPackage);
    }

    public FoodPackage getFoodPackageById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Food package ID cannot be empty");
        }
        
        Optional<FoodPackage> packageOpt = foodPackageRepository.findById(id);
        if (!packageOpt.isPresent()) {
            throw new IllegalArgumentException("Food package not found");
        }
        
        return packageOpt.get();
    }

    public List<FoodPackage> getAllFoodPackages() {
        return foodPackageRepository.findAll();
    }

    public FoodPackage updateFoodPackage(FoodPackage foodPackage) {
        if (foodPackage.getId() == null || foodPackage.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Food package ID cannot be empty");
        }
        
        if (!foodPackageRepository.existsById(foodPackage.getId())) {
            throw new IllegalArgumentException("Food package not found");
        }
        
        return foodPackageRepository.save(foodPackage);
    }

    public void deleteFoodPackage(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Food package ID cannot be empty");
        }
        
        if (!foodPackageRepository.existsById(id)) {
            throw new IllegalArgumentException("Food package not found");
        }
        
        foodPackageRepository.deleteById(id);
    }

    public List<FoodPackage> getFoodPackagesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        
        return foodPackageRepository.findByCategory(category);
    }

    public List<FoodPackage> getAvailableFoodPackages() {
        return foodPackageRepository.findByAvailability(true);
    }

    public List<String> getAllCategories() {
        return foodPackageRepository.findAll().stream()
                .map(FoodPackage::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}
