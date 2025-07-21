package com.wams.service;

import com.wams.model.*;
import com.wams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftTemplateService {

    @Autowired
    private ShiftTemplateRepository templateRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ShiftRepository shiftRepo;

    // Create a template
    public ShiftTemplate createTemplate(Long managerId, ShiftTemplate template) {
        Manager manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        template.setCreatedBy(manager);
        return templateRepo.save(template);
    }

    // View all templates by manager
    public List<ShiftTemplate> getTemplatesByManager(Long managerId) {
        Manager manager = managerRepo.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        return templateRepo.findByCreatedBy(manager);
    }

    // Assign an employee to a template for a set of future days
    public List<Shift> assignTemplateToEmployee(Long templateId, Long employeeId, int numberOfDays) {
        ShiftTemplate template = templateRepo.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Shift> createdShifts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < numberOfDays; i++) {
            Shift shift = new Shift();
            shift.setRoleTitle(template.getRoleTitle());
            shift.setStartTime(template.getStartTime());
            shift.setEndTime(template.getEndTime());
            shift.setRequiredEmployees(template.getDefaultRequiredEmployees());
            shift.setCurrentAssigned(1);
            shift.setDate(today.plusDays(i));
            shift.setAssignedEmployee(employee);
            shift.setStatus("ASSIGNED");
            shift.setOpenForVolunteers(false);
            shift.setCreatedBy(template.getCreatedBy());

            createdShifts.add(shiftRepo.save(shift));
        }

        return createdShifts;
    }
}
