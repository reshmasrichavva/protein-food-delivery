package com.proteinfood.app.repository;

import com.proteinfood.app.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteById(String id) {
        users.remove(id);
    }

    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean existsByEmail(String email) {
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
