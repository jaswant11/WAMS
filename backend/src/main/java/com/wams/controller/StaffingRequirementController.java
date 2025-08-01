package com.wams.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wams.dto.StaffingRequirementDTO;
import com.wams.service.StaffingRequirementService;
@RestController
@RequestMapping("/staffing")
public class StaffingRequirementController {

    @Autowired
    private StaffingRequirementService staffingRequirementService;

    // ✅ Manager sets staffing requirement for a date and shiftTemplate
  @PostMapping("/set")
public ResponseEntity<String> setStaffingRequirement(@RequestBody StaffingRequirementDTO dto) {
    LocalDate parsedDate = LocalDate.parse(dto.getDate());
    staffingRequirementService.setStaffingRequirement(parsedDate, dto.getShiftTemplateId(), dto.getRequiredEmployees());
    return ResponseEntity.ok("Staffing requirement saved successfully.");
}

    // ✅ Manager checks staffing evaluation for a shift
    @GetMapping("/evaluate")
    public ResponseEntity<Map<String, Object>> evaluateStaffing(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long templateId) {

        Map<String, Object> result = staffingRequirementService.evaluateStaffing(date, templateId);
        return ResponseEntity.ok(result);
    }

    // ✅ EMPLOYEE accepts open shift
    @PostMapping("/accept-open")
    public ResponseEntity<String> acceptOpenShift(
            @RequestParam Long employeeId,
            @RequestParam Long shiftId) {

        staffingRequirementService.acceptOpenShift(employeeId, shiftId);
        return ResponseEntity.ok("You have been assigned to this shift.");
    }

    // ✅ EMPLOYEE accepts VTO and removes themselves
    @PostMapping("/accept-vto")
    public ResponseEntity<String> acceptVTO(
            @RequestParam Long employeeId,
            @RequestParam Long shiftId) {

        staffingRequirementService.acceptVTO(employeeId, shiftId);
        return ResponseEntity.ok("You have been removed from this shift.");
    }
}
