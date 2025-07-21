package com.wams.controller;

import com.wams.model.Availability;
import com.wams.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping("/submit/{userId}")
    public ResponseEntity<Availability> submitAvailability(
            @PathVariable Long userId,
            @RequestBody Availability availability) {
        return ResponseEntity.ok(availabilityService.submitAvailability(userId, availability));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Availability>> getAvailabilityByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityByUser(userId));
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<Boolean> isAvailableOnDate(
            @PathVariable Long userId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(availabilityService.isAvailableOnDate(userId, localDate));
    }
}
