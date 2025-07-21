package com.wams.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "shift_templates")
public class ShiftTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dayOfWeek; // e.g. "MONDAY", "TUESDAY", etc.
    private LocalTime startTime;
    private LocalTime endTime;
    private int requiredEmployees;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public int getRequiredEmployees() { return requiredEmployees; }
    public void setRequiredEmployees(int requiredEmployees) { this.requiredEmployees = requiredEmployees; }
    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }
}
