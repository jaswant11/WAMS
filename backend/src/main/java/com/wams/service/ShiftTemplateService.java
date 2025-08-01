package com.wams.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.dto.ShiftTemplateDTO;
import com.wams.model.Employee;
import com.wams.model.Shift;
import com.wams.model.ShiftStatus;
import com.wams.model.ShiftTemplate;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ShiftRepository;
import com.wams.repository.ShiftTemplateRepository;

@Service
public class ShiftTemplateService {

    @Autowired
    private ShiftTemplateRepository shiftTemplateRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public ShiftTemplate createShiftTemplate(ShiftTemplateDTO dto) {
        
        ShiftTemplate template = new ShiftTemplate();
        template.setName(dto.getName());
        template.setStartTime(LocalTime.parse(dto.getStartTime()));
        template.setEndTime(LocalTime.parse(dto.getEndTime()));
template.setStartDate(LocalDate.parse(dto.getStartDate()));// ✅ missing in your output
        template.setEndDate(LocalDate.parse(dto.getEndDate()));   

        Set<DayOfWeek> days = dto.getDaysOfWeek().stream()
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());
        template.setDaysOfWeek(days);
System.out.println("DTO startDate: " + dto.getStartDate());
System.out.println("DTO endDate: " + dto.getEndDate());
        return shiftTemplateRepository.save(template);
        
    }

public void assignTemplateToEmployees(Long templateId, List<Long> employeeIds, LocalDate startDate, LocalDate endDate) {
    ShiftTemplate template = shiftTemplateRepository.findById(templateId)
            .orElseThrow(() -> new RuntimeException("Template not found"));

    List<Employee> employees = employeeRepository.findAllById(employeeIds);

    LocalDate date = startDate;
    while (!date.isAfter(endDate)) {
        if (template.getDaysOfWeek().contains(date.getDayOfWeek())) {
            // Check if shift for this date already exists
            List<Shift> existingShifts = shiftRepository.findByShiftTemplateAndDate(template, date);
            Shift shift;

            if (!existingShifts.isEmpty()) {
                shift = existingShifts.get(0);
            } else {
                shift = new Shift();
                shift.setDate(date);
                shift.setStartTime(template.getStartTime());
                shift.setEndTime(template.getEndTime());
                shift.setTemplate(template);
                shift.setStatus(ShiftStatus.ASSIGNED);
            }

            for (Employee emp : employees) {
                if (shift.getEmployees() == null || !shift.getEmployees().contains(emp)) {
                    shift.addEmployee(emp);
                }
            }

            shiftRepository.save(shift);
        }

        date = date.plusDays(1);
    }
}

}

