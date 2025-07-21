package com.wams.controller;

import com.wams.model.Shift;
import com.wams.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    // Manager creates a new shift
    @PostMapping("/create/{managerId}")
    public ResponseEntity<Shift> createShift(@PathVariable Long managerId, @RequestBody Shift shift) {
        return ResponseEntity.ok(shiftService.createShift(managerId, shift));
    }

    // Assign shift to employee
    @PostMapping("/{shiftId}/assign/{employeeId}")
    public ResponseEntity<Shift> assignShift(@PathVariable Long shiftId, @PathVariable Long employeeId) {
        return ResponseEntity.ok(shiftService.assignShift(shiftId, employeeId));
    }

    // Mark shift as complete
    @PutMapping("/{shiftId}/complete")
    public ResponseEntity<Shift> completeShift(@PathVariable Long shiftId) {
        return ResponseEntity.ok(shiftService.completeShift(shiftId));
    }

    // Get all shifts (admin or manager view)
    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    // Get shifts assigned to a specific employee
    @GetMapping("/employee/{employeeId}")
    public List<Shift> getShiftsForEmployee(@PathVariable Long employeeId) {
        return shiftService.getShiftsForEmployee(employeeId);
    }

    // Get shifts created by a specific manager
    @GetMapping("/manager/{managerId}")
    public List<Shift> getShiftsByManager(@PathVariable Long managerId) {
        return shiftService.getShiftsByManager(managerId);
    }
}
