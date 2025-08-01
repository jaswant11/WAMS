package com.wams.dto;

public class EmployeeDetailDTO {
    private Long id;
    private String username;
    private String name;
    private Boolean available;
    private String department;

    public EmployeeDetailDTO(Long id, String username, String name, Boolean available, String department) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.available = available;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public Boolean getAvailable() { return available; }
    public String getDepartment() { return department; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) { this.name = name; }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setDepartment(String department) { this.department = department; }
}
