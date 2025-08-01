package com.wams.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
   
import com.wams.dto.WorklogDTO;
import com.wams.dto.WorklogResponseDTO;
import com.wams.dto.WorklogSummaryDTO;
import com.wams.model.Worklog;
import com.wams.repository.WorklogRepository;
import com.wams.service.WorklogService;

@RestController
@RequestMapping("/employee/worklog")
public class WorklogController{

    @Autowired
    private WorklogService worklogService;
    


@Autowired
private WorklogRepository worklogRepository;




    @PostMapping("/worklog/submit")
    public ResponseEntity<String> submitWorklog(@RequestBody WorklogDTO dto) {
        worklogService.submitWorklog(dto);
        return ResponseEntity.ok("Worklog submitted successfully.");
    }


    // 1. Endpoint to get all entries between two dates
    @GetMapping("/view/entries/{employeeId}")
    public ResponseEntity<List<WorklogSummaryDTO>> getWorklogEntries(
            @PathVariable Long employeeId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
         return ResponseEntity.ok(worklogService.getWorklogsForEmployeeBetweenDates(employeeId, start, end));
    }

    // 2. Endpoint to get entries + total hours
    @GetMapping("/view/summary/{employeeId}")
    public ResponseEntity<WorklogResponseDTO> getWorklogSummaryWithTotal(
            @PathVariable Long employeeId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(worklogService.getWorklogSummaryWithTotal(employeeId, start, end));
    }
   // ✅ Manager views any employee's worklog summary with total hours
  @GetMapping("/all")
public ResponseEntity<List<Worklog>> getAllWorklogs() {
    return ResponseEntity.ok(worklogRepository.findAll());
}

}

