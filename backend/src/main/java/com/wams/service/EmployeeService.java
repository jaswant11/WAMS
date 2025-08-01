package com.wams.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.dto.EmployeeDetailDTO;
import com.wams.model.User;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.UserRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EmployeeDetailDTO> getAllEmployeeDetails() {
        return employeeRepository.findAll().stream()
                .map(emp -> {
                    Optional<User> userOpt = userRepository.findById(emp.getId());
                    String username = userOpt.map(User::getUsername).orElse("unknown");
                    return new EmployeeDetailDTO(emp.getId(), username, emp.getName(), emp.getAvailable(), emp.getDepartment());
                }).collect(Collectors.toList());
    }

    public List<EmployeeDetailDTO> filterByAvailabilityAndDepartment(Boolean available, String department) {
        return employeeRepository.findAll().stream()
                .filter(emp ->
                        (available == null || emp.getAvailable() == available) &&
                        (department == null || emp.getDepartment().equalsIgnoreCase(department)))
                .map(emp -> {
                    Optional<User> userOpt = userRepository.findById(emp.getId());
                    String username = userOpt.map(User::getUsername).orElse("unknown");
                    return new EmployeeDetailDTO(emp.getId(), username, emp.getName(), emp.getAvailable(), emp.getDepartment());
                }).collect(Collectors.toList());
    }
}
