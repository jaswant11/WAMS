package com.wams.service;

import com.wams.model.User;
import com.wams.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all employees
    public List<User> getAllEmployees() {
        return employeeRepository.findByRole("employee");
    }

    // Get one employee by ID
    public User getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Update an employee
    public User updateEmployee(Long id, User updatedEmployee) {
        User existing = getEmployeeById(id);
        if (existing == null) return null;

        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setPassword(updatedEmployee.getPassword());
        existing.setRole("employee"); // ensure role remains "employee"

        return employeeRepository.save(existing);
    }
}

