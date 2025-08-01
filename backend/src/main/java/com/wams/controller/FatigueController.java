package com.wams.controller;
import com.wams.dto.FatigueRecommendationDTO;
import java.util.List;
import com.wams.dto.FatigueDTO;
import com.wams.service.FatigueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatigue")
public class FatigueController {

    @Autowired
    private FatigueService fatigueService;

    // Employee views their own fatigue
    @GetMapping("/employee/{employeeId}")
    public FatigueDTO getEmployeeFatigue(@PathVariable Long employeeId) {
        return fatigueService.calculateFatigue(employeeId);
    }

    // Manager checks any employee's fatigue
    @GetMapping("/manager/{employeeId}")
    public FatigueDTO getManagerViewOfEmployeeFatigue(@PathVariable Long employeeId) {
        return fatigueService.calculateFatigue(employeeId);
    }
    // Manager gets worker fatigue-based recommendations
@GetMapping("/recommendation")
public List<FatigueRecommendationDTO> getRecommendations() {
    return fatigueService.getRecommendations();
}

//
}
    