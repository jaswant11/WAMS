package com.wams.dto;

public class StaffingRequirementDTO {
    private String date;
    private Long shiftTemplateId;
    private int requiredEmployees;

    public StaffingRequirementDTO() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getShiftTemplateId() {
        return shiftTemplateId;
    }

    public void setShiftTemplateId(Long shiftTemplateId) {
        this.shiftTemplateId = shiftTemplateId;
    }

    public int getRequiredEmployees() {
        return requiredEmployees;
    }

    public void setRequiredEmployees(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }
}
