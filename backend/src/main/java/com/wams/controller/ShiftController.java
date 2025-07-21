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

    @PostMapping
    public ResponseEntity<Shift> createShift(@RequestBody Shift shift) {
        return ResponseEntity.ok(shiftService.createShift(shift));
    }

    @GetMapping
    public List<Shift> getAllShifts() {
        return shiftService.getAllShifts();
    }

    @GetMapping("/employee/{employeeId}")
    public List<Shift> getShiftsForEmployee(@PathVariable Long employeeId) {
        return shiftService.getShiftsForEmployee(employeeId);
    }

    @PostMapping("/{shiftId}/assign/{userId}")
    public ResponseEntity<Shift> assignShift(
        @PathVariable Long shiftId,
        @PathVariable Long userId) {
        return ResponseEntity.ok(shiftService.assignShift(shiftId, userId));
    }
}
