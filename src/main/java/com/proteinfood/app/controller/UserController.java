package com.proteinfood.app.controller;

import com.proteinfood.app.model.User;
import com.proteinfood.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Web page for register
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Process registration
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            User registeredUser = userService.register(user);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // Login page
    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) Boolean registered) {
        if (registered != null && registered) {
            model.addAttribute("successMessage", "Registration successful! Please log in.");
        }
        return "login";
    }

    // Process login
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, 
                        HttpSession session, Model model) {
        try {
            User user = userService.authenticate(username, password);
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getId());
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    // User profile page
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "profile";
    }

    // Update profile
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        try {
            user.setId(currentUser.getId());
            User updatedUser = userService.updateUser(user);
            session.setAttribute("currentUser", updatedUser);
            model.addAttribute("user", updatedUser);
            model.addAttribute("successMessage", "Profile updated successfully!");
            return "profile";
        } catch (Exception e) {
            model.addAttribute("user", currentUser);
            model.addAttribute("error", e.getMessage());
            return "profile";
        }
    }

    // REST API Endpoints
    @PostMapping("/api/users/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.register(user);
            // Remove password from response
            newUser.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/users/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            // Remove password from response
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
