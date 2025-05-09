package com.proteinfood.app.repository;

import com.proteinfood.app.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {
    private Map<String, Order> orders = new HashMap<>();

    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public void deleteById(String id) {
        orders.remove(id);
    }

    public List<Order> findByUserId(String userId) {
        return orders.values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Order> findByStatus(String status) {
        return orders.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    
    public boolean existsById(String id) {
        return orders.containsKey(id);
    }
}
