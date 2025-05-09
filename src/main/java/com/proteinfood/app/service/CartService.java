package com.proteinfood.app.service;

import com.proteinfood.app.model.CartItem;
import com.proteinfood.app.model.FoodPackage;
import com.proteinfood.app.repository.CartRepository;
import com.proteinfood.app.repository.FoodPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private FoodPackageRepository foodPackageRepository;

    public CartItem addItemToCart(String userId, CartItem item) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (item.getFoodPackageId() == null || item.getFoodPackageId().trim().isEmpty()) {
            throw new IllegalArgumentException("Food package ID cannot be empty");
        }
        if (item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        // Verify that the food package exists
        Optional<FoodPackage> foodPackageOpt = foodPackageRepository.findById(item.getFoodPackageId());
        if (!foodPackageOpt.isPresent()) {
            throw new IllegalArgumentException("Food package not found");
        }
        
        FoodPackage foodPackage = foodPackageOpt.get();
        if (!foodPackage.isAvailable()) {
            throw new IllegalArgumentException("Food package is not available");
        }
        
        // Ensure the item has the correct price
        item.setPricePerUnit(foodPackage.getPrice());
        
        return cartRepository.addItem(userId, item);
    }

    public List<CartItem> getUserCart(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        return cartRepository.getCartItems(userId);
    }

    public boolean removeItemFromCart(String userId, String itemId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be empty");
        }
        
        return cartRepository.removeItem(userId, itemId);
    }

    public boolean updateCartItemQuantity(String userId, String itemId, int quantity) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (itemId == null || itemId.trim().isEmpty()) {
            throw new IllegalArgumentException("Item ID cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        return cartRepository.updateItemQuantity(userId, itemId, quantity);
    }

    public void clearCart(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        cartRepository.clearCart(userId);
    }

    public BigDecimal getCartTotal(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        return cartRepository.getCartTotal(userId);
    }
    
    public int getCartItemCount(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        return cartRepository.getCartItemCount(userId);
    }
}
