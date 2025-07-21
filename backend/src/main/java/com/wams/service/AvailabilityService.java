package com.wams.service;

import com.wams.model.Availability;
import com.wams.model.Employee;
import com.wams.repository.AvailabilityRepository;
import com.wams.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Availability submitAvailability(Long employeeId, Availability availability) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        availability.setEmployee(employee);
        return availabilityRepo.save(availability);
    }

    public List<Availability> getAvailabilityForEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        return availabilityRepo.findByEmployee(employee);
    }

    public List<Availability> getAvailabilityBetweenDates(Long employeeId, String start, String end) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return availabilityRepo.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }
}
