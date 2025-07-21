package com.wams.controller;

import com.wams.model.Worklog;
import com.wams.service.WorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/worklogs")
public class WorklogController {

    @Autowired
    private WorklogService worklogService;

    @PostMapping("/submit/{userId}")
    public ResponseEntity<Worklog> addWorklog(
            @PathVariable Long userId,
            @RequestBody Worklog worklog) {
        return ResponseEntity.ok(worklogService.addWorklog(userId, worklog));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Worklog>> getWorklogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(worklogService.getWorklogsByUser(userId));
    }

    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Worklog>> getWorklogsByUserBetweenDates(
            @PathVariable Long userId,
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(worklogService.getWorklogsByUserBetweenDates(userId, startDate, endDate));
    }
}
