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

    public String getFatigueScore(Long employeeId) {
        double hours = getNumericFatigueScore(employeeId);
        if (hours <= 40) return "Low Fatigue (🟢)";
        else if (hours <= 55) return "Moderate Fatigue (🟠)";
        else return "High Fatigue (🔴)";
    }

    public double getNumericFatigueScore(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate now = LocalDate.now();
        LocalDate start = now.minusDays(7);

        List<Worklog> logs = worklogRepo.findByEmployeeAndDateBetween(employee, start, now);
        return logs.stream().mapToDouble(Worklog::getHoursWorked).sum();
    }
}
