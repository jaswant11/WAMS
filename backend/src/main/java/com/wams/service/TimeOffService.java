package com.wams.service;

import com.wams.model.TimeOff;
import com.wams.model.Employee;
import com.wams.model.Manager;
import com.wams.repository.TimeOffRepository;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ManagerRepository managerRepo;

    // Submit a new time-off request
    public TimeOff requestTimeOff(Long employeeId, TimeOff timeOffRequest) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        timeOffRequest.setEmployee(employee);
        timeOffRequest.setStatus("PENDING");

        return timeOffRepo.save(timeOffRequest);
    }

    // Approve or deny a time-off request
    public TimeOff updateRequestStatus(Long requestId, Long managerId, String status) {
        TimeOff request = timeOffRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Time-off request not found"));

        Manager manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        request.setManager(manager);
        request.setStatus(status.toUpperCase()); // APPROVED or DENIED

        return timeOffRepo.save(request);
    }

    // Get all time-off requests submitted by an employee
    public List<TimeOff> getRequestsByEmployee(Long employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return timeOffRepo.findByEmployee(employee);
    }

    // Optional: Get all time-off requests (for admin or manager view)
    public List<TimeOff> getAllRequests() {
        return timeOffRepo.findAll();
    }
}
