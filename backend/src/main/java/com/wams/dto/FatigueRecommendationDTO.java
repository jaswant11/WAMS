package com.wams.dto;

public class FatigueRecommendationDTO {
    private Long employeeId;
    private String employeeName;
    private double fatigueScore;

    public FatigueRecommendationDTO() {
    }

    public FatigueRecommendationDTO(Long employeeId, String employeeName, double fatigueScore) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.fatigueScore = fatigueScore;
    }

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

    public double getFatigueScore() {
        return fatigueScore;
    }

    public void setFatigueScore(double fatigueScore) {
        this.fatigueScore = fatigueScore;
    }
}
