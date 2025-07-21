package com.wams.controller;

import com.wams.model.Shift;
import com.wams.model.ShiftTemplate;
import com.wams.service.ShiftTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class ShiftTemplateController {

    @Autowired
    private ShiftTemplateService templateService;

    @PostMapping("/create/{managerId}")
    public ShiftTemplate createTemplate(@PathVariable Long managerId, @RequestBody ShiftTemplate template) {
        return templateService.createTemplate(managerId, template);
    }

    @GetMapping("/manager/{managerId}")
    public List<ShiftTemplate> getTemplatesByManager(@PathVariable Long managerId) {
        return templateService.getTemplatesByManager(managerId);
    }

    @PostMapping("/assign/{templateId}/to/{employeeId}")
    public List<Shift> assignTemplateToEmployee(
            @PathVariable Long templateId,
            @PathVariable Long employeeId,
            @RequestParam int days) {
        return templateService.assignTemplateToEmployee(templateId, employeeId, days);
    }
}
