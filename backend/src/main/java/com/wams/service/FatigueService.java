package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.Worklog;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FatigueService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private WorklogRepository worklogRepo;

    public int getFatigueScore(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();

        // Last 7 days work logs
        LocalDate now = LocalDate.now();
        List<Worklog> logs = worklogRepo.findByEmployeeAndDateBetween(employee, now.minusDays(7), now);

        int totalHours = logs.stream()
                .mapToInt(Worklog::getHoursWorked)
                .sum();

        // Simple fatigue calculation
        if (totalHours >= 60) return 100;
        if (totalHours >= 50) return 90;
        if (totalHours >= 40) return 75;
        if (totalHours >= 30) return 60;
        if (totalHours >= 20) return 45;
        return 30;
    }
}
