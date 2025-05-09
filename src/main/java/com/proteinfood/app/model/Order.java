package com.proteinfood.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private String id;
    private String userId;
    private List<CartItem> items;
    private BigDecimal total;
    private String status;
    private LocalDateTime orderDate;
    private String deliveryAddress;
    private String paymentMethod;
    private String trackingNumber;

    public enum Status {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = Status.PENDING.name();
    }

    public Order(String userId, List<CartItem> items, BigDecimal total, String status, 
                LocalDateTime orderDate, String deliveryAddress, String paymentMethod) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.trackingNumber = "TR" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void addItem(CartItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }
}
