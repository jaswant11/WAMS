package com.wams.model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private ShiftStatus status;

    // A shift can have multiple employees assigned
    @ManyToMany
    @JoinTable(
        name = "shift_employees",
        joinColumns = @JoinColumn(name = "shift_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    // Link to the shift template used to generate this shift
    @ManyToOne
    @JoinColumn(name = "template_id")
    private ShiftTemplate shiftTemplate;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ShiftStatus getStatus() {
        return status;
    }

    public void setStatus(ShiftStatus status) {
        this.status = status;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public ShiftTemplate getShiftTemplate() {
        return shiftTemplate;
    }

    public void setShiftTemplate(ShiftTemplate shiftTemplate) {
        this.shiftTemplate = shiftTemplate;
    }
    public void setTemplate(ShiftTemplate template) {
    this.shiftTemplate = template;
}

public void addEmployee(Employee employee) {
    if (this.employees == null) {
        this.employees = new ArrayList<>();
    }
    this.employees.add(employee);
}
}
