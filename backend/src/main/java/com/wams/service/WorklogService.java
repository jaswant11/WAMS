package com.wams.service;

import com.wams.model.Worklog;
import com.wams.model.Employee;
import com.wams.repository.WorklogRepository;
import com.wams.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Worklog> getAllWorklogs() {
        return worklogRepository.findAll();
    }

    public Worklog addWorklog(Long employeeId, Worklog worklog) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            worklog.setEmployee(employee.get());
            return worklogRepository.save(worklog);
        } else {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
    }

    public List<Worklog> getWorklogsByEmployee(Long employeeId) {
        return worklogRepository.findByEmployeeId(employeeId);
    }

    public List<Worklog> getWorklogsByEmployeeAndDate(Long employeeId, LocalDate date) {
        return worklogRepository.findByEmployeeIdAndDate(employeeId, date);
    }

    public void deleteWorklog(Long id) {
        worklogRepository.deleteById(id);
    }
}
