package com.wams.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class StaffingRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "shift_template_id")
    private ShiftTemplate shiftTemplate;

    private int requiredEmployees;
    @ManyToOne
@JoinColumn(name = "shift_id")
private Shift shift;

public void setShift(Shift shift) {
    this.shift = shift;
}

public Shift getShift() {
    return shift;
}

    public StaffingRequirement() {}

    public StaffingRequirement(LocalDate date, ShiftTemplate shiftTemplate, int requiredEmployees) {
        this.date = date;
        this.shiftTemplate = shiftTemplate;
        this.requiredEmployees = requiredEmployees;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShiftTemplate getShiftTemplate() {
        return shiftTemplate;
    }

    public void setShiftTemplate(ShiftTemplate shiftTemplate) {
        this.shiftTemplate = shiftTemplate;
    }

    public int getRequiredEmployees() {
        return requiredEmployees;
    }

    public void setRequiredEmployees(int requiredEmployees) {
        this.requiredEmployees = requiredEmployees;
    }
    
}
