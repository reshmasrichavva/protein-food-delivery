package com.proteinfood.app.repository;

import com.proteinfood.app.model.ProgressTracking;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProgressTrackingRepository {
    private Map<String, ProgressTracking> progressTrackings = new HashMap<>();

    public ProgressTracking save(ProgressTracking progressTracking) {
        progressTrackings.put(progressTracking.getId(), progressTracking);
        return progressTracking;
    }

    public Optional<ProgressTracking> findById(String id) {
        return Optional.ofNullable(progressTrackings.get(id));
    }

    public List<ProgressTracking> findAll() {
        return new ArrayList<>(progressTrackings.values());
    }

    public void deleteById(String id) {
        progressTrackings.remove(id);
    }

    public List<ProgressTracking> findByUserId(String userId) {
        return progressTrackings.values().stream()
                .filter(tracking -> tracking.getUserId().equals(userId))
                .sorted(Comparator.comparing(ProgressTracking::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Optional<ProgressTracking> findByUserIdAndDate(String userId, LocalDate date) {
        return progressTrackings.values().stream()
                .filter(tracking -> tracking.getUserId().equals(userId) && tracking.getDate().equals(date))
                .findFirst();
    }
    
    public boolean existsById(String id) {
        return progressTrackings.containsKey(id);
    }
}
