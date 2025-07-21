package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.TimeOff;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.TimeOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public TimeOff requestTimeOff(Long employeeId, TimeOff timeOff) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        timeOff.setEmployee(employee);
        return timeOffRepo.save(timeOff);
    }

    public List<TimeOff> getTimeOffForEmployee(Long employeeId) {
        return timeOffRepo.findByEmployeeId(employeeId);
    }

    public List<TimeOff> getTimeOffBetweenDates(Long employeeId, String start, String end) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return timeOffRepo.findByEmployeeAndDateBetween(employee, startDate, endDate);
    }
}
