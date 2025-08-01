package com.wams.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.dto.FatigueDTO;
import com.wams.dto.FatigueRecommendationDTO;
import com.wams.model.Employee;
import com.wams.model.Worklog;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.WorklogRepository;

@Service
public class FatigueService {
    @Autowired
private EmployeeRepository employeeRepository;

    @Autowired
    private WorklogRepository worklogRepository;

    public FatigueDTO calculateFatigue(Long employeeId) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(7);

        List<Worklog> logs = worklogRepository.findByEmployeeIdAndDateBetween(employeeId, sevenDaysAgo, today);
        long totalHours = logs.stream()
                .mapToLong(log -> java.time.Duration.between(log.getStartTime(), log.getEndTime()).toHours())
                .sum();

        double fatigueScore = Math.min((totalHours / 60.0) * 100.0, 100.0);

        FatigueDTO dto = new FatigueDTO();
        dto.setEmployeeId(employeeId);
        dto.setTotalHours(totalHours);
        dto.setFatigueScore(fatigueScore);

        return dto;
    }
    public List<FatigueRecommendationDTO> getRecommendations() {
    List<Employee> employees = employeeRepository.findAll();

    return employees.stream()
        .map(emp -> {
            FatigueDTO fatigue = calculateFatigue(emp.getId());
            return new FatigueRecommendationDTO(emp.getId(), emp.getName(), fatigue.getFatigueScore());
        })
        .sorted((a, b) -> Double.compare(a.getFatigueScore(), b.getFatigueScore()))
        .collect(Collectors.toList());
}
// ✅ ADDED: Get recommended employees for shift based on fatigue score
  public List<FatigueRecommendationDTO> getRecommendedEmployeesForShift(LocalDate date, Long templateId) {
    List<Employee> allEmployees = employeeRepository.findAll();
    List<Employee> unassigned = new ArrayList<>();

    for (Employee emp : allEmployees) {
        boolean assigned = emp.getShifts().stream()
                .anyMatch(shift -> shift.getDate().equals(date) &&
                        shift.getShiftTemplate().getId().equals(templateId));
        if (!assigned) {
            unassigned.add(emp);
        }
    }

    return unassigned.stream()
            .map(emp -> {
                FatigueDTO fatigue = calculateFatigue(emp.getId());
                return new FatigueRecommendationDTO(emp.getId(), emp.getName(), fatigue.getFatigueScore());
            })
            .sorted(Comparator.comparingDouble(FatigueRecommendationDTO::getFatigueScore))
            .collect(Collectors.toList());
}

}
