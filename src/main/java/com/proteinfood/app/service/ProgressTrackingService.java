package com.proteinfood.app.service;

import com.proteinfood.app.model.ProgressTracking;
import com.proteinfood.app.repository.ProgressTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressTrackingService {

    @Autowired
    private ProgressTrackingRepository progressTrackingRepository;

    public ProgressTracking createProgressTracking(ProgressTracking tracking) {
        // Validate input
        if (tracking.getUserId() == null || tracking.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        // Check if tracking for the same date already exists
        Optional<ProgressTracking> existingTracking = 
                progressTrackingRepository.findByUserIdAndDate(tracking.getUserId(), tracking.getDate());
        
        if (existingTracking.isPresent()) {
            // Update existing tracking instead of creating a new one
            ProgressTracking existing = existingTracking.get();
            existing.setWeight(tracking.getWeight());
            existing.setBodyFatPercentage(tracking.getBodyFatPercentage());
            existing.setMusclePercentage(tracking.getMusclePercentage());
            existing.setCaloriesConsumed(tracking.getCaloriesConsumed());
            existing.setProteinConsumed(tracking.getProteinConsumed());
            existing.setNotes(tracking.getNotes());
            
            return progressTrackingRepository.save(existing);
        }
        
        return progressTrackingRepository.save(tracking);
    }

    public ProgressTracking getProgressTrackingById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Progress tracking ID cannot be empty");
        }
        
        Optional<ProgressTracking> trackingOpt = progressTrackingRepository.findById(id);
        if (!trackingOpt.isPresent()) {
            throw new IllegalArgumentException("Progress tracking not found");
        }
        
        return trackingOpt.get();
    }

    public List<ProgressTracking> getProgressTrackingByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        
        return progressTrackingRepository.findByUserId(userId);
    }

    public ProgressTracking getProgressTrackingByUserIdAndDate(String userId, LocalDate date) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        
        Optional<ProgressTracking> trackingOpt = progressTrackingRepository.findByUserIdAndDate(userId, date);
        if (!trackingOpt.isPresent()) {
            throw new IllegalArgumentException("Progress tracking not found for this date");
        }
        
        return trackingOpt.get();
    }

    public void deleteProgressTracking(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Progress tracking ID cannot be empty");
        }
        
        if (!progressTrackingRepository.existsById(id)) {
            throw new IllegalArgumentException("Progress tracking not found");
        }
        
        progressTrackingRepository.deleteById(id);
    }
}
