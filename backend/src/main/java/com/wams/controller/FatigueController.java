package com.wams.controller;

import com.wams.service.FatigueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fatigue")
public class FatigueController {

    @Autowired
    private FatigueService fatigueService;

    @GetMapping("/score/{employeeId}")
    public String getFatigueScore(@PathVariable Long employeeId) {
        return fatigueService.getFatigueScore(employeeId);
    }
}
