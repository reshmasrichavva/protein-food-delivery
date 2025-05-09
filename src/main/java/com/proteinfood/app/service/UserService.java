package com.proteinfood.app.service;

import com.proteinfood.app.model.User;
import com.proteinfood.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        // Validate input
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        
        return user;
    }

    public User getUserById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        
        return userOpt.get();
    }

    public User updateUser(User user) {
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (!existingUserOpt.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        
        User existingUser = existingUserOpt.get();
        
        // Update only provided fields (non-null)
        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            // Check if username is being changed and if it already exists
            if (!existingUser.getUsername().equals(user.getUsername()) &&
                userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            existingUser.setUsername(user.getUsername());
        }
        
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            // Check if email is being changed and if it already exists
            if (!existingUser.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            existingUser.setEmail(user.getEmail());
        }
        
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        
        if (user.getFullName() != null && !user.getFullName().trim().isEmpty()) {
            existingUser.setFullName(user.getFullName());
        }
        
        if (user.getAddress() != null) {
            existingUser.setAddress(user.getAddress());
        }
        
        if (user.getPhone() != null) {
            existingUser.setPhone(user.getPhone());
        }
        
        return userRepository.save(existingUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        
        userRepository.deleteById(id);
    }
}
