package com.wams.controller;

import com.wams.model.FatigueScore;
import com.wams.service.FatigueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fatigue")
public class FatigueController {

    @Autowired
    private FatigueService fatigueService;

    // Get fatigue score for an employee
    @GetMapping("/employee/{id}")
    public ResponseEntity<FatigueScore> getFatigueByEmployeeId(@PathVariable Long id) {
        return ResponseEntity.ok(fatigueService.getFatigueScoreByEmployeeId(id));
    }

    // Admin/Manager: Get all fatigue scores
    @GetMapping("/all")
    public ResponseEntity<List<FatigueScore>> getAllFatigueScores() {
        return ResponseEntity.ok(fatigueService.getAllFatigueScores());
    }

    // Update fatigue score (manually or triggered)
    @PostMapping("/update")
    public ResponseEntity<FatigueScore> updateFatigue(@RequestBody FatigueScore score) {
        return ResponseEntity.ok(fatigueService.updateFatigueScore(score));
    }
}
