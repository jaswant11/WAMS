package com.wams.controller;

import com.wams.model.Employee;
import com.wams.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    @PostMapping
    public void add(@RequestBody Employee employee) {
        service.addEmployee(employee);
    }
}
