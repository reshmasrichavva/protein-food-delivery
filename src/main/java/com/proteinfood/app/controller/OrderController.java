package com.proteinfood.app.controller;

import com.proteinfood.app.model.CartItem;
import com.proteinfood.app.model.Order;
import com.proteinfood.app.model.User;
import com.proteinfood.app.service.CartService;
import com.proteinfood.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;

    // Page to view user's orders
    @GetMapping("/orders")
    public String getUserOrders(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<Order> orders = orderService.getOrdersByUserId(currentUser.getId());
        model.addAttribute("orders", orders);
        return "orders";
    }

    // Page to view order details
    @GetMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable String id, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            Order order = orderService.getOrderById(id);
            
            // Security check - only allow users to see their own orders
            if (!order.getUserId().equals(currentUser.getId())) {
                return "redirect:/orders";
            }
            
            model.addAttribute("order", order);
            return "order-detail";
        } catch (IllegalArgumentException e) {
            return "redirect:/orders";
        }
    }

    // Create order from cart
    @PostMapping("/checkout")
    public String createOrder(HttpSession session, 
                             @RequestParam String deliveryAddress,
                             @RequestParam String paymentMethod,
                             Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<CartItem> cartItems = cartService.getUserCart(currentUser.getId());
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "cart";
        }
        
        // Create order
        Order order = new Order();
        order.setUserId(currentUser.getId());
        order.setItems(cartItems);
        order.setTotal(cartService.getCartTotal(currentUser.getId()));
        order.setStatus(Order.Status.PENDING.name());
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod);
        
        try {
            Order newOrder = orderService.createOrder(order);
            
            // Clear the cart after successful order
            cartService.clearCart(currentUser.getId());
            
            return "redirect:/orders/" + newOrder.getId() + "?success=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "cart";
        }
    }

    // REST API Endpoints
    @PostMapping("/api/orders")
    @ResponseBody
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order newOrder = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/orders")
    @ResponseBody
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/api/orders/{id}")
    @ResponseBody
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PutMapping("/api/orders/{id}")
    @ResponseBody
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestBody Order order) {
        try {
            order.setId(id);
            Order updatedOrder = orderService.updateOrder(order);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/api/orders/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
