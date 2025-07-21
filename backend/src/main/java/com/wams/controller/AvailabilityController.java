package com.wams.controller;

import com.wams.model.Availability;
import com.wams.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Employee submits availability
    @PostMapping("/submit")
    public ResponseEntity<Availability> submitAvailability(@RequestBody Availability availability) {
        Availability saved = availabilityService.saveAvailability(availability);
        return ResponseEntity.ok(saved);
    }

    // Get availability by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Availability>> getAvailabilityByEmployee(@PathVariable Long employeeId) {
        List<Availability> result = availabilityService.getAvailabilityByEmployee(employeeId);
        return ResponseEntity.ok(result);
    }

    // Admin/Manager gets all availability
    @GetMapping("/all")
    public ResponseEntity<List<Availability>> getAllAvailability() {
        return ResponseEntity.ok(availabilityService.getAllAvailability());
    }
}
