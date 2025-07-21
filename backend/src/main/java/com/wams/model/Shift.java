package com.wams.model;

import jakarta.persistence.*;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleTitle;     // e.g., "Loader", "Cashier"
    private String date;          // Format: YYYY-MM-DD
    private String startTime;
    private String endTime;

    private int requiredEmployees;
    private int currentAssigned;
    private String status;        // OPEN, ASSIGNED, COMPLETED

    private boolean isOpenForVolunteers = false;

    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    private Employee assignedEmployee;

    @ManyToOne
    @JoinColumn(name = "created_by_manager_id")
    private Manager createdBy;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getRequiredEmployees() { return requiredEmployees; }
    public void setRequiredEmployees(int requiredEmployees) { this.requiredEmployees = requiredEmployees; }

    public int getCurrentAssigned() { return currentAssigned; }
    public void setCurrentAssigned(int currentAssigned) { this.currentAssigned = currentAssigned; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isOpenForVolunteers() { return isOpenForVolunteers; }
    public void setOpenForVolunteers(boolean openForVolunteers) { isOpenForVolunteers = openForVolunteers; }

    public Employee getAssignedEmployee() { return assignedEmployee; }
    public void setAssignedEmployee(Employee assignedEmployee) { this.assignedEmployee = assignedEmployee; }

    public Manager getCreatedBy() { return createdBy; }
    public void setCreatedBy(Manager createdBy) { this.createdBy = createdBy; }
}
