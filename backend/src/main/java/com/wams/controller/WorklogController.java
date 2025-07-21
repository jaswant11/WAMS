package com.wams.controller;

import com.wams.model.WorkLog;
import com.wams.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worklogs")
public class WorklogController {

    @Autowired
    private WorkLogService workLogService;

    // Employee submits a worklog
    @PostMapping("/submit")
    public ResponseEntity<WorkLog> submitWorkLog(@RequestBody WorkLog log) {
        return ResponseEntity.ok(workLogService.saveWorkLog(log));
    }

    // Get all worklogs for an employee
    @GetMapping("/employee/{userId}")
    public ResponseEntity<List<WorkLog>> getLogsByEmployee(@PathVariable Long userId) {
        return ResponseEntity.ok(workLogService.getWorkLogsByUser(userId));
    }

    // Admin/Manager: Get all logs
    @GetMapping("/all")
    public ResponseEntity<List<WorkLog>> getAllLogs() {
        return ResponseEntity.ok(workLogService.getAllWorkLogs());
    }

    // Optional: Delete a worklog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
        return ResponseEntity.ok("Worklog deleted.");
    }
}
