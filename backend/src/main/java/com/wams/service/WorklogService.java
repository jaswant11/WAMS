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
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Worklog logWork(Long employeeId, Worklog worklog) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        worklog.setEmployee(employee);
        return worklogRepo.save(worklog);
    }

    public List<Worklog> getWorklogsForEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        return worklogRepo.findByEmployeeAndDateBetween(
                employee,
                LocalDate.now().minusDays(30),
                LocalDate.now()
        );
    }

    public List<Worklog> getWorklogsBetweenDates(Long employeeId, String start, String end) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return worklogRepo.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }
}
