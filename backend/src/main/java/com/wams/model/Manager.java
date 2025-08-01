package com.wams.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {

    @Id
    private Long id; // Matches user.id

    private String name;

    private String department;

    private String username;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
