package com.wams.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.model.Employee;
import com.wams.model.Shift;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ShiftRepository;
import com.wams.repository.ShiftTemplateRepository;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftTemplateRepository shiftTemplateRepository;

    public void removeEmployeeFromShiftsInDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        List<Shift> shifts = shiftRepository.findByDateBetween(startDate, endDate);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        for (Shift shift : shifts) {
            if (shift.getEmployees().contains(employee)) {
                shift.getEmployees().remove(employee);
                shiftRepository.save(shift);
            }
        }
    }

    public void removeEmployeeFromShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        shift.getEmployees().remove(employee);
        shiftRepository.save(shift);
    }

    public void assignEmployeeToShift(Long shiftId, Long employeeId) {
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!shift.getEmployees().contains(employee)) {
            shift.getEmployees().add(employee);
            shiftRepository.save(shift);
        }
    }
}
