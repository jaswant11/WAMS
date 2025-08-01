package com.wams.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wams.dto.EmployeeDetailDTO;
import com.wams.model.Employee;
import com.wams.repository.EmployeeRepository;
import com.wams.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    // ✅ Update employee details
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updated) {
        Optional<Employee> opt = employeeRepository.findById(id);
        if (opt.isPresent()) {
            Employee emp = opt.get();
            if (updated.getName() != null) emp.setName(updated.getName());
            if (updated.getAvailable() != null) emp.setAvailable(updated.getAvailable());
            if (updated.getDepartment() != null) emp.setDepartment(updated.getDepartment());
            return ResponseEntity.ok(employeeRepository.save(emp));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // ✅ View all employees (joined with user)
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDetailDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployeeDetails());
    }

    // ✅ Filter employees by availability and department
    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeDetailDTO>> filterEmployees(
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String department) {
        return ResponseEntity.ok(employeeService.filterByAvailabilityAndDepartment(available, department));
    }
    @GetMapping("/profile/{username}")
public ResponseEntity<Employee> getEmployeeProfile(@PathVariable String username) {
    Employee employee = employeeRepository.findByUserUsername(username);
    if (employee == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(employee);
}
}
