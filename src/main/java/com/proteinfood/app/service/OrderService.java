package com.proteinfood.app.service;

import com.proteinfood.app.model.Order;
import com.proteinfood.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        // Validate input
        if (order.getUserId() == null || order.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        if (order.getTotal() == null) {
            throw new IllegalArgumentException("Order total cannot be empty");
        }
        if (order.getDeliveryAddress() == null || order.getDeliveryAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Delivery address cannot be empty");
        }
        if (order.getPaymentMethod() == null || order.getPaymentMethod().trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }
        
        return orderRepository.save(order);
    }

    public Order getOrderById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (!orderOpt.isPresent()) {
            throw new IllegalArgumentException("Order not found");
        }
        
        return orderOpt.get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        
        return orderRepository.findByStatus(status);
    }

    public Order updateOrder(Order order) {
        if (order.getId() == null || order.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        
        if (!orderRepository.existsById(order.getId())) {
            throw new IllegalArgumentException("Order not found");
        }
        
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        
        orderRepository.deleteById(id);
    }
}
