package com.wams.controller;

import com.wams.model.TimeOff;
import com.wams.service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeoff")
public class TimeOffController {

    @Autowired
    private TimeOffService service;

    @GetMapping
    public List<TimeOff> getAll() {
        return service.getAllRequests();
    }

    @PostMapping
    public void request(@RequestBody TimeOff request) {
        service.requestTimeOff(request);
    }
}
