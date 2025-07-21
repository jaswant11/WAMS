package com.wams.service;

import com.wams.model.Availability;
import com.wams.model.Employee;
import com.wams.repository.AvailabilityRepository;
import com.wams.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Availability submitAvailability(Long employeeId, Availability availability) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            availability.setEmployee(employeeOpt.get());
            return availabilityRepository.save(availability);
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public List<Availability> getAvailabilityByEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            return availabilityRepository.findByEmployee(employeeOpt.get());
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public boolean isAvailableOnDate(Long employeeId, LocalDate date) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            List<Availability> availabilities = availabilityRepository
                .findByEmployeeAndDate(employeeOpt.get(), date);
            return !availabilities.isEmpty();
        }
        return false;
    }

    public List<Availability> getAvailabilityBetween(Long employeeId, LocalDate start, LocalDate end) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            return availabilityRepository.findByEmployeeAndDateBetween(employeeOpt.get(), start, end);
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }
}
