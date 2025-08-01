package com.wams.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorklogDTO {
    private Long employeeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
