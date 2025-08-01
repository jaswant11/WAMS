package com.wams.dto;

public class ManagerDetailDTO {
    private Long id;
    private String username;
    private String name;
    private String department;

    public ManagerDetailDTO(Long id, String username, String name, String department) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
}
