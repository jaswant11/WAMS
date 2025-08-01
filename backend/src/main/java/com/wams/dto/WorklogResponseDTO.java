package com.wams.dto;

import java.util.List;

public class WorklogResponseDTO {
    private List<WorklogSummaryDTO> entries;
    private long totalHours;

    private Long employeeId;
    private String employeeName;

    // Getters and Setters for entries and totalHours
    public List<WorklogSummaryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<WorklogSummaryDTO> entries) {
        this.entries = entries;
    }

    public long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(long totalHours) {
        this.totalHours = totalHours;
    }

    // 🔧 Add these new getters/setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
         this.employeeName = employeeName;
    }

}