package com.wams.controller;

import com.wams.model.TimeOff;
import com.wams.service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/timeoff")
public class TimeOffController {

    @Autowired
    private TimeOffService timeOffService;

    @PostMapping("/submit")
    public TimeOff submitRequest(@RequestBody TimeOff timeOff) {
        return timeOffService.requestTimeOff(timeOff);
    }

    @GetMapping("/view/{employeeId}")
    public List<TimeOff> viewRequests(@PathVariable Long employeeId) {
        return timeOffService.getRequestsByEmployee(employeeId);
    }
}
