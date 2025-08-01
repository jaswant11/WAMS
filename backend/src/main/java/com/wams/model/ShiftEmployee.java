package com.wams.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shift_employees")
public class ShiftEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shift_id")
    private Long shiftId;

    @Column(name = "employee_id")
    private Long employeeId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
