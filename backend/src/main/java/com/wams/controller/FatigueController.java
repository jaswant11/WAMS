package com.wams.controller;

import com.wams.service.FatigueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatigue")
public class FatigueController {

    @Autowired
    private FatigueService fatigueService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Integer> getFatigueScore(@PathVariable Long userId) {
        int score = fatigueService.getFatigueScore(userId);
        return ResponseEntity.ok(score);
    }
}
