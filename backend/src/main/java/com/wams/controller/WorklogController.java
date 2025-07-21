package com.wams.controller;

import com.wams.model.Worklog;
import com.wams.service.WorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worklogs")
public class WorklogController {

    @Autowired
    private WorklogService worklogService;

    // View all worklogs for an employee
    @GetMapping("/employee/{id}")
    public List<Worklog> getWorklogsForEmployee(@PathVariable Long id) {
        return worklogService.getWorklogsForEmployee(id);
    }
}
