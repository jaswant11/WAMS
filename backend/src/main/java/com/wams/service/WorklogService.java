package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.Shift;
import com.wams.model.Worklog;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.WorklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Worklog logShiftCompletion(Shift shift) {
        if (shift.getAssignedEmployee() == null) {
            throw new RuntimeException("No employee assigned to this shift.");
        }

        Worklog log = new Worklog();
        log.setEmployee(shift.getAssignedEmployee());
        log.setDate(shift.getDate());

        double hoursWorked = calculateHours(shift.getStartTime(), shift.getEndTime());
        log.setHoursWorked(hoursWorked);

        return worklogRepo.save(log);
    }

    public List<Worklog> getWorklogsForEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return worklogRepo.findByEmployee(employee);
    }

    public List<Worklog> getWorklogsBetweenDates(Employee employee, LocalDate from, LocalDate to) {
        return worklogRepo.findByEmployeeAndDateBetween(employee, from, to);
    }

    private double calculateHours(String start, String end) {
        try {
            int startHour = Integer.parseInt(start.split(":")[0]);
            int endHour = Integer.parseInt(end.split(":")[0]);
            return endHour - startHour;
        } catch (Exception e) {
            throw new RuntimeException("Invalid time format");
        }
    }
}
