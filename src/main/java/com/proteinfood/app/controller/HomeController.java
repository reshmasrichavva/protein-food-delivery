package com.proteinfood.app.controller;

import com.proteinfood.app.model.FoodPackage;
import com.proteinfood.app.service.CartService;
import com.proteinfood.app.service.FoodPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private FoodPackageService foodPackageService;
    
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // Get featured food packages (just get all and take first few)
        List<FoodPackage> allPackages = foodPackageService.getAllFoodPackages();
        List<FoodPackage> featuredPackages = allPackages.stream()
                .filter(FoodPackage::isAvailable)
                .limit(4)
                .collect(Collectors.toList());
        
        model.addAttribute("featuredPackages", featuredPackages);
        
        // Add categories for menu
        List<String> categories = foodPackageService.getAllCategories();
        model.addAttribute("categories", categories);
        
        // Add cart count if user is logged in
        if (session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            int cartCount = cartService.getCartItemCount(userId);
            model.addAttribute("cartCount", cartCount);
        }
        
        return "index";
    }
}
