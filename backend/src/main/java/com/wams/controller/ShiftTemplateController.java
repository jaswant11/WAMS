package com.wams.controller;

import com.wams.model.ShiftTemplate;
import com.wams.service.ShiftTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shift-templates")
public class ShiftTemplateController {

    @Autowired
    private ShiftTemplateService shiftTemplateService;

    // Create a new shift template
    @PostMapping
    public ResponseEntity<ShiftTemplate> createTemplate(@RequestBody ShiftTemplate template) {
        return ResponseEntity.ok(shiftTemplateService.createShiftTemplate(template));
    }

    // Get all shift templates
    @GetMapping
    public List<ShiftTemplate> getAllTemplates() {
        return shiftTemplateService.getAllShiftTemplates();
    }

    // Get template by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShiftTemplate> getTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(shiftTemplateService.getShiftTemplateById(id));
    }

    // Assign employees to a shift template
    @PostMapping("/{templateId}/assign-employees")
    public ResponseEntity<String> assignEmployeesToTemplate(
            @PathVariable Long templateId,
            @RequestBody List<Long> employeeIds
    ) {
        shiftTemplateService.assignEmployeesToTemplate(templateId, employeeIds);
        return ResponseEntity.ok("Employees assigned to template successfully.");
    }
}