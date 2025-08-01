package com.wams.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wams.dto.ShiftTemplateDTO;
import com.wams.model.ShiftTemplate;
import com.wams.repository.ShiftTemplateRepository;
import com.wams.service.ShiftTemplateService;
@RestController
@RequestMapping("/manager/shift-template")
public class ShiftTemplateController {

    @Autowired
    private ShiftTemplateService shiftTemplateService;
    @Autowired
private ShiftTemplateRepository shiftTemplateRepository;


    // ✅ Create a shift template
    @PostMapping("/create")
    public ResponseEntity<ShiftTemplate> createTemplate(@RequestBody ShiftTemplateDTO dto) {
        ShiftTemplate created = shiftTemplateService.createShiftTemplate(dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Assign shift template to one or more employees for a date range
   
@PostMapping("/assign/{templateId}")
public ResponseEntity<String> assignTemplateToEmployees(
    @PathVariable Long templateId,
    @RequestParam List<Long> employeeIds,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
) {
    shiftTemplateService.assignTemplateToEmployees(templateId, employeeIds, startDate, endDate);
    return ResponseEntity.ok("Shift template assigned and shifts generated.");
}


}

