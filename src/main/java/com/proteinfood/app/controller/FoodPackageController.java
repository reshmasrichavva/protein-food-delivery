package com.proteinfood.app.controller;

import com.proteinfood.app.model.FoodPackage;
import com.proteinfood.app.service.FoodPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FoodPackageController {

    @Autowired
    private FoodPackageService foodPackageService;

    // Web page for all food packages
    @GetMapping("/food-packages")
    public String getAllFoodPackages(Model model, 
                                    @RequestParam(required = false) String category) {
        List<FoodPackage> packages;
        
        if (category != null && !category.isEmpty()) {
            packages = foodPackageService.getFoodPackagesByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            packages = foodPackageService.getAllFoodPackages();
        }
        
        model.addAttribute("foodPackages", packages);
        
        // Add categories for filter
        List<String> categories = foodPackageService.getAllCategories();
        model.addAttribute("categories", categories);
        
        return "food-packages";
    }

    // Web page for food package details
    @GetMapping("/food-packages/{id}")
    public String getFoodPackageDetails(@PathVariable String id, Model model) {
        try {
            FoodPackage foodPackage = foodPackageService.getFoodPackageById(id);
            model.addAttribute("foodPackage", foodPackage);
            
            // Get related packages in same category
            List<FoodPackage> relatedPackages = foodPackageService.getFoodPackagesByCategory(foodPackage.getCategory());
            relatedPackages.removeIf(fp -> fp.getId().equals(id)); // Remove current package
            
            model.addAttribute("relatedPackages", relatedPackages.subList(0, Math.min(3, relatedPackages.size())));
            
            return "food-package-detail";
        } catch (IllegalArgumentException e) {
            return "redirect:/food-packages";
        }
    }

    // REST API Endpoints
    @PostMapping("/api/food-packages")
    @ResponseBody
    public ResponseEntity<?> createFoodPackage(@RequestBody FoodPackage foodPackage) {
        try {
            FoodPackage newFoodPackage = foodPackageService.createFoodPackage(foodPackage);
            return ResponseEntity.status(HttpStatus.CREATED).body(newFoodPackage);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/food-packages")
    @ResponseBody
    public List<FoodPackage> getAllFoodPackagesApi() {
        return foodPackageService.getAllFoodPackages();
    }

    @GetMapping("/api/food-packages/{id}")
    @ResponseBody
    public ResponseEntity<?> getFoodPackageByIdApi(@PathVariable String id) {
        try {
            FoodPackage foodPackage = foodPackageService.getFoodPackageById(id);
            return ResponseEntity.ok(foodPackage);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PutMapping("/api/food-packages/{id}")
    @ResponseBody
    public ResponseEntity<?> updateFoodPackage(@PathVariable String id, 
                                              @RequestBody FoodPackage foodPackage) {
        try {
            foodPackage.setId(id);
            FoodPackage updatedFoodPackage = foodPackageService.updateFoodPackage(foodPackage);
            return ResponseEntity.ok(updatedFoodPackage);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/api/food-packages/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteFoodPackage(@PathVariable String id) {
        try {
            foodPackageService.deleteFoodPackage(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
