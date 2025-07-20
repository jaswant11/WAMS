package com.wams.model;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;
    private boolean available;

    public Employee() {}

    public Employee(String name, String role, boolean available) {
        this.name = name;
        this.role = role;
        this.available = available;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}