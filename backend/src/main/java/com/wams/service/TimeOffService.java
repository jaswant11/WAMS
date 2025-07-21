package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.TimeOff;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.TimeOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public TimeOff requestTimeOff(Long employeeId, TimeOff timeOff) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            timeOff.setEmployee(employeeOpt.get());
            return timeOffRepository.save(timeOff);
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public List<TimeOff> getTimeOffsByEmployee(Long employeeId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            return timeOffRepository.findByEmployee(employeeOpt.get());
        }
        throw new RuntimeException("Employee not found with ID: " + employeeId);
    }

    public boolean hasTimeOffOnDate(Long employeeId, LocalDate date) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isPresent()) {
            List<TimeOff> timeOffs = timeOffRepository
                .findByEmployeeAndStartDateLessThanEqualAndEndDateGreaterThanEqual(employeeOpt.get(), date, date);
            return !timeOffs.isEmpty();
        }
        return false;
    }
}
