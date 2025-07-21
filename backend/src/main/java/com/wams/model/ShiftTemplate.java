package com.wams.model;

import jakarta.persistence.*;

@Entity
public class ShiftTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleTitle;
    private String startTime;
    private String endTime;

    private int defaultRequiredEmployees;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager createdBy;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getDefaultRequiredEmployees() { return defaultRequiredEmployees; }
    public void setDefaultRequiredEmployees(int defaultRequiredEmployees) {
        this.defaultRequiredEmployees = defaultRequiredEmployees;
    }

    public Manager getCreatedBy() { return createdBy; }
    public void setCreatedBy(Manager createdBy) { this.createdBy = createdBy; }
}
