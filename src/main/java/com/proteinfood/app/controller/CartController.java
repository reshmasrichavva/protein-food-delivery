package com.proteinfood.app.controller;

import com.proteinfood.app.model.CartItem;
import com.proteinfood.app.model.FoodPackage;
import com.proteinfood.app.model.User;
import com.proteinfood.app.service.CartService;
import com.proteinfood.app.service.FoodPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private FoodPackageService foodPackageService;

    // View cart page
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<CartItem> cartItems = cartService.getUserCart(currentUser.getId());
        BigDecimal cartTotal = cartService.getCartTotal(currentUser.getId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        
        return "cart";
    }

    // Add item to cart from detail page
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String foodPackageId,
                           @RequestParam int quantity,
                           HttpSession session, 
                           Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            FoodPackage foodPackage = foodPackageService.getFoodPackageById(foodPackageId);
            
            CartItem cartItem = new CartItem(
                foodPackage.getId(),
                foodPackage.getName(),
                quantity,
                foodPackage.getPrice()
            );
            
            cartService.addItemToCart(currentUser.getId(), cartItem);
            
            return "redirect:/food-packages/" + foodPackageId + "?added=true";
        } catch (IllegalArgumentException e) {
            return "redirect:/food-packages";
        }
    }

    // Update cart item quantity
    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam String itemId,
                                @RequestParam int quantity,
                                HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        cartService.updateCartItemQuantity(currentUser.getId(), itemId, quantity);
        return "redirect:/cart";
    }

    // Remove item from cart
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam String itemId,
                                HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        cartService.removeItemFromCart(currentUser.getId(), itemId);
        return "redirect:/cart";
    }

    // Clear cart
    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        cartService.clearCart(currentUser.getId());
        return "redirect:/cart";
    }

    // REST API Endpoints
    @GetMapping("/api/users/{userId}/cart")
    @ResponseBody
    public List<CartItem> getUserCart(@PathVariable String userId) {
        return cartService.getUserCart(userId);
    }

    @PostMapping("/api/users/{userId}/cart")
    @ResponseBody
    public ResponseEntity<?> addToCartApi(@PathVariable String userId, 
                                         @RequestBody CartItem cartItem) {
        try {
            cartService.addItemToCart(userId, cartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartService.getUserCart(userId));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/api/users/{userId}/cart/{itemId}")
    @ResponseBody
    public ResponseEntity<?> updateCartItemApi(@PathVariable String userId,
                                             @PathVariable String itemId,
                                             @RequestParam int quantity) {
        try {
            boolean updated = cartService.updateCartItemQuantity(userId, itemId, quantity);
            if (updated) {
                return ResponseEntity.ok(cartService.getUserCart(userId));
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Item not found or invalid quantity");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/api/users/{userId}/cart/{itemId}")
    @ResponseBody
    public ResponseEntity<?> removeFromCartApi(@PathVariable String userId,
                                             @PathVariable String itemId) {
        try {
            boolean removed = cartService.removeItemFromCart(userId, itemId);
            if (removed) {
                return ResponseEntity.ok(cartService.getUserCart(userId));
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Item not found");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/api/users/{userId}/cart")
    @ResponseBody
    public ResponseEntity<?> clearCartApi(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
