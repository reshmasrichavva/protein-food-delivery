package com.proteinfood.app.repository;

import com.proteinfood.app.model.CartItem;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CartRepository {
    // Map: userId -> List of CartItems
    private Map<String, List<CartItem>> userCarts = new HashMap<>();

    public CartItem addItem(String userId, CartItem item) {
        if (!userCarts.containsKey(userId)) {
            userCarts.put(userId, new ArrayList<>());
        }
        
        // Check if item already exists
        Optional<CartItem> existingItem = userCarts.get(userId).stream()
                .filter(cartItem -> cartItem.getFoodPackageId().equals(item.getFoodPackageId()))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // Update quantity and total price if item exists
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.setTotalPrice(cartItem.getPricePerUnit().multiply(new BigDecimal(cartItem.getQuantity())));
            return cartItem;
        } else {
            // Add new item
            userCarts.get(userId).add(item);
            return item;
        }
    }

    public List<CartItem> getCartItems(String userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    public boolean removeItem(String userId, String itemId) {
        if (!userCarts.containsKey(userId)) {
            return false;
        }
        
        List<CartItem> userCart = userCarts.get(userId);
        return userCart.removeIf(item -> item.getId().equals(itemId));
    }

    public boolean updateItemQuantity(String userId, String itemId, int quantity) {
        if (!userCarts.containsKey(userId) || quantity < 1) {
            return false;
        }
        
        Optional<CartItem> itemOpt = userCarts.get(userId).stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
        
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            item.setTotalPrice(item.getPricePerUnit().multiply(new BigDecimal(quantity)));
            return true;
        }
        
        return false;
    }

    public void clearCart(String userId) {
        userCarts.put(userId, new ArrayList<>());
    }

    public BigDecimal getCartTotal(String userId) {
        if (!userCarts.containsKey(userId)) {
            return BigDecimal.ZERO;
        }
        
        return userCarts.get(userId).stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public int getCartItemCount(String userId) {
        if (!userCarts.containsKey(userId)) {
            return 0;
        }
        
        return userCarts.get(userId).stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
