package com.wams.controller;

import com.wams.model.Availability;
import com.wams.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService service;

    @GetMapping
    public List<Availability> getAll() {
        return service.getAll();
    }

    @PostMapping
    public void add(@RequestBody Availability availability) {
        service.add(availability);
    }
}
