package com.wams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wams.model.Availability;
import com.wams.service.AvailabilityService;

@RestController
@RequestMapping("/employee/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Submit availability
    @PostMapping("/submit")
    public ResponseEntity<Availability> submitAvailability(@RequestBody Availability availability) {
        return ResponseEntity.ok(availabilityService.submitAvailability(availability));
    }

    // Get availability for current employee
    @GetMapping("/{employeeId}")
    public ResponseEntity<List<Availability>> getAvailability(@PathVariable Long employeeId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityByEmployee(employeeId));
    }
}
