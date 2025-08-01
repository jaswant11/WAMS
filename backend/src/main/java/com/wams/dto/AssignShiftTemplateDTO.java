package com.wams.dto;

import java.util.List;

public class AssignShiftTemplateDTO {
    private Long templateId;
    private List<Long> employeeIds;
    private String startDate;
    private String endDate;

    // Getters and Setters
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
