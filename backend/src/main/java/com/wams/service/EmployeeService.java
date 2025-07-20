package com.wams.service;

import com.wams.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private List<Employee> fakeRepo = new ArrayList<>(); // temporary in-memory list

    public List<Employee> getAllEmployees() {
        return fakeRepo;
    }

    public void addEmployee(Employee employee) {
        fakeRepo.add(employee);
    }
}
