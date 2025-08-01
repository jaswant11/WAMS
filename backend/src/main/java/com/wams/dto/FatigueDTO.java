package com.wams.dto;

public class FatigueDTO {
    private Long employeeId;
    private long totalHours;
    private double fatigueScore;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public long getTotalHours() { return totalHours; }
    public void setTotalHours(long totalHours) { this.totalHours = totalHours; }

    public double getFatigueScore() { return fatigueScore; }
    public void setFatigueScore(double fatigueScore) { this.fatigueScore = fatigueScore; }
}

