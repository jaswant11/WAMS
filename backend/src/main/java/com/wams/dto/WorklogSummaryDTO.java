package com.wams.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorklogSummaryDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private long totalHours;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getTotalHours() { return totalHours; }
    public void setTotalHours(long totalHours) { this.totalHours = totalHours; }
}
