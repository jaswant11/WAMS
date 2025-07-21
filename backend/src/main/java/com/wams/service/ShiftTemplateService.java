package com.wams.service;

import com.wams.model.Employee;
import com.wams.model.Manager;
import com.wams.model.ShiftTemplate;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.ManagerRepository;
import com.wams.repository.ShiftTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftTemplateService {

    @Autowired
    private ShiftTemplateRepository templateRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public ShiftTemplate createTemplate(Long managerId, ShiftTemplate template) {
        Manager manager = managerRepo.findById(managerId).orElseThrow();
        template.setManager(manager);
        return templateRepo.save(template);
    }

    public List<ShiftTemplate> getTemplatesByManager(Long managerId) {
        Manager manager = managerRepo.findById(managerId).orElseThrow();
        return templateRepo.findByManager(manager);
    }

    public ShiftTemplate assignEmployeeToTemplate(Long templateId, Long employeeId) {
        ShiftTemplate template = templateRepo.findById(templateId).orElseThrow();
        Employee employee = employeeRepo.findById(employeeId).orElseThrow();
        template.getEmployees().add(employee);
        return templateRepo.save(template);
    }
}

