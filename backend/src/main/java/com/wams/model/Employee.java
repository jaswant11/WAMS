package com.wams.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private Long id;  // Matches user.id

    private String name;

    private Boolean available;

    private String department;

    private String username;
@ManyToMany(mappedBy = "employees")
private List<Shift> shifts;
    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Shift> getShifts() {
    return shifts;
}

public void setShifts(List<Shift> shifts) {
    this.shifts = shifts;
}
@OneToOne
@JoinColumn(name = "user_id")
private User user;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
