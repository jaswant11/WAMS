package com.wams.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "shift_template")
public class ShiftTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "template_days", joinColumns = @JoinColumn(name = "template_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Set<DayOfWeek> daysOfWeek;

    private LocalDate startDate;

    private LocalDate endDate;
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
    name = "template_employees",
    joinColumns = @JoinColumn(name = "template_id"),
    inverseJoinColumns = @JoinColumn(name = "employee_id")
)
private Set<Employee> employees;
    private String name;

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

    // === Getters ===
    public Long getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

   public Set<Employee> getEmployees() {
    return employees;
}

public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
}


    // === Setters ===
    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

  
}
