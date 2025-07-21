package com.wams.controller;

import com.wams.model.TimeOffRequest;
import com.wams.service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeoff")
public class TimeOffController {

    @Autowired
    private TimeOffService timeOffService;

    // Employee submits a time-off request
    @PostMapping("/request")
    public ResponseEntity<TimeOffRequest> requestTimeOff(@RequestBody TimeOffRequest request) {
        return ResponseEntity.ok(timeOffService.submitRequest(request));
    }

    // Manager/Admin gets all pending requests
    @GetMapping("/pending")
    public List<TimeOffRequest> getAllPendingRequests() {
        return timeOffService.getPendingRequests();
    }

    // Manager approves a request
    @PutMapping("/approve/{requestId}")
    public ResponseEntity<String> approve(@PathVariable Long requestId) {
        timeOffService.approveRequest(requestId);
        return ResponseEntity.ok("Time-off request approved.");
    }

    // Manager denies a request
    @PutMapping("/deny/{requestId}")
    public ResponseEntity<String> deny(@PathVariable Long requestId) {
        timeOffService.denyRequest(requestId);
        return ResponseEntity.ok("Time-off request denied.");
    }

    // Get all requests by employee ID
    @GetMapping("/employee/{employeeId}")
    public List<TimeOffRequest> getRequestsByEmployee(@PathVariable Long employeeId) {
        return timeOffService.getRequestsByEmployee(employeeId);
    }
}
