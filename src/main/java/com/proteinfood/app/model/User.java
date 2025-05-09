package com.proteinfood.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private List<Order> orders;
    private List<ProgressTracking> progressTrackings;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.orders = new ArrayList<>();
        this.progressTrackings = new ArrayList<>();
    }

    public User(String username, String email, String password, String fullName, String address, String phone) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.orders = new ArrayList<>();
        this.progressTrackings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<ProgressTracking> getProgressTrackings() {
        return progressTrackings;
    }

    public void setProgressTrackings(List<ProgressTracking> progressTrackings) {
        this.progressTrackings = progressTrackings;
    }

    public void addOrder(Order order) {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        this.orders.add(order);
    }

    public void addProgressTracking(ProgressTracking tracking) {
        if (this.progressTrackings == null) {
            this.progressTrackings = new ArrayList<>();
        }
        this.progressTrackings.add(tracking);
    }
}
