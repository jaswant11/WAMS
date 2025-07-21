package com.wams.service;

import com.wams.model.User;
import com.wams.model.Worklog;
import com.wams.repository.UserRepository;
import com.wams.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FatigueService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private UserRepository userRepository;

    // Returns a fatigue score (0 = fresh, 100 = max fatigue) based on hours worked in last 7 days
    public int getFatigueScore(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate now = LocalDate.now();
        LocalDate weekAgo = now.minusDays(7);

        List<Worklog> logs = worklogRepository.findByEmployeeAndDateBetween(user, weekAgo, now);

        int totalHours = logs.size() * 8; // Adjust as needed, or sum actual hours from worklog fields

        if (totalHours >= 60) return 100;
        if (totalHours >= 50) return 90;
        if (totalHours >= 40) return 75;
        if (totalHours >= 30) return 60;
        if (totalHours >= 20) return 45;
        return 30;
    }
}
