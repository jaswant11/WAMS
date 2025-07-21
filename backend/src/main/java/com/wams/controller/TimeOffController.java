package com.wams.controller;

import com.wams.model.TimeOff;
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

    @PostMapping("/request/{userId}")
    public ResponseEntity<TimeOff> requestTimeOff(
            @PathVariable Long userId,
            @RequestBody TimeOff timeOff) {
        return ResponseEntity.ok(timeOffService.requestTimeOff(userId, timeOff));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TimeOff>> getTimeOffsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(timeOffService.getTimeOffsByUser(userId));
    }
}
