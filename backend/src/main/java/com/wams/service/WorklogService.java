package com.wams.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.dto.WorklogDTO;
import com.wams.dto.WorklogResponseDTO;
import com.wams.dto.WorklogSummaryDTO; // ✅ Import this
import com.wams.model.Employee;
import com.wams.model.Worklog;
import com.wams.repository.EmployeeRepository;
import com.wams.repository.WorklogRepository;

@Service
public class WorklogService {

    @Autowired
    private WorklogRepository worklogRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void submitWorklog(WorklogDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow();
        Worklog worklog = new Worklog();
        worklog.setEmployee(employee);
        worklog.setDate(dto.getDate());
        worklog.setStartTime(dto.getStartTime());
        worklog.setEndTime(dto.getEndTime());
        worklog.setDescription(dto.getDescription());
        worklogRepository.save(worklog);
    }

    public List<WorklogSummaryDTO> getWorklogsForEmployeeBetweenDates(Long employeeId, LocalDate start, LocalDate end) {
        List<Worklog> worklogs = worklogRepository.findByEmployeeIdAndDateBetween(employeeId, start, end);
        return worklogs.stream().map(worklog -> {
            WorklogSummaryDTO dto = new WorklogSummaryDTO();
            dto.setDate(worklog.getDate());
            dto.setStartTime(worklog.getStartTime());
            dto.setEndTime(worklog.getEndTime());
            dto.setDescription(worklog.getDescription());

            long hours = Duration.between(worklog.getStartTime(), worklog.getEndTime()).toHours();
            dto.setTotalHours(hours);
            return dto;
        }).collect(Collectors.toList());
    }

    public WorklogResponseDTO getWorklogSummaryWithTotal(Long employeeId, LocalDate start, LocalDate end) {
        List<Worklog> worklogs = worklogRepository.findByEmployeeIdAndDateBetween(employeeId, start, end);

        List<WorklogSummaryDTO> entries = worklogs.stream().map(worklog -> {
            WorklogSummaryDTO dto = new WorklogSummaryDTO();
            dto.setDate(worklog.getDate());
            dto.setStartTime(worklog.getStartTime());
            dto.setEndTime(worklog.getEndTime());
            dto.setDescription(worklog.getDescription());

            long hours = Duration.between(worklog.getStartTime(), worklog.getEndTime()).toHours();
            dto.setTotalHours(hours);
            return dto;
        }).collect(Collectors.toList());

        long totalHours = entries.stream().mapToLong(WorklogSummaryDTO::getTotalHours).sum();

        WorklogResponseDTO response = new WorklogResponseDTO();
        response.setEntries(entries);
        response.setTotalHours(totalHours);

        return response;
    }
      // ✅ Manager: View all logs from all employees
 public List<WorklogResponseDTO> getAllWorklogsForManager() {
    return worklogRepository.findAll().stream()
        .collect(Collectors.groupingBy(w -> w.getEmployee().getId()))
        .entrySet()
        .stream()
        .map(entry -> {
            Long employeeId = entry.getKey();
            List<Worklog> logs = entry.getValue();

            List<WorklogSummaryDTO> summaries = logs.stream().map(w -> {
                WorklogSummaryDTO dto = new WorklogSummaryDTO();
                dto.setDate(w.getDate());
                dto.setStartTime(w.getStartTime());
                dto.setEndTime(w.getEndTime());
                dto.setDescription(w.getDescription());
                dto.setTotalHours(Duration.between(w.getStartTime(), w.getEndTime()).toHours());
                return dto;
            }).toList();

            long totalHours = summaries.stream().mapToLong(WorklogSummaryDTO::getTotalHours).sum();

            WorklogResponseDTO response = new WorklogResponseDTO();
            response.setEntries(summaries);
            response.setTotalHours(totalHours);
            response.setEmployeeId(employeeId);
            response.setEmployeeName(logs.get(0).getEmployee().getName());

            return response;
        }).toList();
}

    
}
