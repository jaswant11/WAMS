package com.wams.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Worklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeUsername; // ✅ new field to replace employeeId

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String details;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeUsername() { return employeeUsername; }
    public void setEmployeeUsername(String employeeUsername) { this.employeeUsername = employeeUsername; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    private String description;
   @ManyToOne(fetch = FetchType.LAZY, optional = true)
@JoinColumn(name = "employee_id", referencedColumnName = "id")
private Employee employee;
    // ... other fields

    // ✅ Add this
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ✅ Add this
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
