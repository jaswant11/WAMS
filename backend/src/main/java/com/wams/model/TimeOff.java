package com.wams.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class TimeOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    // Getters and setters
}
