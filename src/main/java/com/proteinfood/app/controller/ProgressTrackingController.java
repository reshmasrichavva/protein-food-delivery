package com.proteinfood.app.controller;

import com.proteinfood.app.model.ProgressTracking;
import com.proteinfood.app.model.User;
import com.proteinfood.app.service.ProgressTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProgressTrackingController {

    @Autowired
    private ProgressTrackingService progressTrackingService;

    // Web page for progress tracking
    @GetMapping("/progress-tracking")
    public String getProgressTracking(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        List<ProgressTracking> trackings = progressTrackingService.getProgressTrackingByUserId(currentUser.getId());
        model.addAttribute("trackings", trackings);
        model.addAttribute("newTracking", new ProgressTracking());
        return "progress-tracking";
    }

    // Add new progress tracking
    @PostMapping("/progress-tracking")
    public String addProgressTracking(@ModelAttribute ProgressTracking tracking,
                                     HttpSession session,
                                     Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        tracking.setUserId(currentUser.getId());
        if (tracking.getDate() == null) {
            tracking.setDate(LocalDate.now());
        }
        
        try {
            ProgressTracking newTracking = progressTrackingService.createProgressTracking(tracking);
            return "redirect:/progress-tracking?success=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<ProgressTracking> trackings = progressTrackingService.getProgressTrackingByUserId(currentUser.getId());
            model.addAttribute("trackings", trackings);
            model.addAttribute("newTracking", tracking);
            return "progress-tracking";
        }
    }

    // REST API Endpoints
    @PostMapping("/api/progress-tracking")
    @ResponseBody
    public ResponseEntity<?> createProgressTracking(@RequestBody ProgressTracking tracking) {
        try {
            ProgressTracking newTracking = progressTrackingService.createProgressTracking(tracking);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTracking);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/api/progress-tracking/{id}")
    @ResponseBody
    public ResponseEntity<?> getProgressTrackingById(@PathVariable String id) {
        try {
            ProgressTracking tracking = progressTrackingService.getProgressTrackingById(id);
            return ResponseEntity.ok(tracking);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/api/users/{userId}/progress-tracking")
    @ResponseBody
    public List<ProgressTracking> getProgressTrackingByUserId(@PathVariable String userId) {
        return progressTrackingService.getProgressTrackingByUserId(userId);
    }

    @GetMapping("/api/users/{userId}/progress-tracking/date/{date}")
    @ResponseBody
    public ResponseEntity<?> getProgressTrackingByUserIdAndDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            ProgressTracking tracking = progressTrackingService.getProgressTrackingByUserIdAndDate(userId, date);
            return ResponseEntity.ok(tracking);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
