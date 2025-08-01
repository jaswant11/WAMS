package com.wams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wams.model.Shift;
import com.wams.repository.ShiftRepository;
import com.wams.service.ShiftService;
@RestController
@RequestMapping("/manager/shift")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;
      @Autowired
private ShiftRepository shiftRepository;

    @PostMapping("/assign")
    public ResponseEntity<String> assignEmployeeToShift(
            @RequestParam Long shiftId,
            @RequestParam Long employeeId) {
        shiftService.assignEmployeeToShift(shiftId, employeeId);
        return ResponseEntity.ok("Employee assigned to shift.");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeEmployeeFromShift(
            @RequestParam Long shiftId,
            @RequestParam Long employeeId) {
        shiftService.removeEmployeeFromShift(shiftId, employeeId);
        return ResponseEntity.ok("Employee removed from shift.");
    }
    @GetMapping("/all")
public List<Shift> getAllShifts() {
    return shiftRepository.findAll();
}

   
}
