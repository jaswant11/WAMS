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

    @PostMapping("/manager/{managerId}")
    public ResponseEntity<ShiftTemplate> createTemplate(
            @PathVariable Long managerId,
            @RequestBody ShiftTemplate template) {
        return ResponseEntity.ok(shiftTemplateService.createTemplate(managerId, template));
    }

    @GetMapping("/manager/{managerId}")
    public List<ShiftTemplate> getTemplatesByManager(@PathVariable Long managerId) {
        return shiftTemplateService.getTemplatesByManager(managerId);
    }
}
