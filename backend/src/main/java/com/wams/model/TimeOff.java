package com.wams.model;

import jakarta.persistence.*;

@Entity
public class TimeOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startDate;
    private String endDate;
    private String reason;

    private String status; // PENDING, APPROVED, DENIED

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager; // who approved/denied it

    // Getters and Setters
}
