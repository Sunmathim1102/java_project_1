package com.skillmatrix.model;

import java.time.LocalDateTime;
import java.util.List;

public class EmployeeResponse {

    private Long id;
    private String employeeCode;
    private String name;
    private String email;
    private String department;
    private String designation;
    private Integer totalCapacity;
    private Integer allocatedCapacity;
    private Integer availableCapacity;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EmployeeSkillResponse> skills;

    public EmployeeResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getAllocatedCapacity() {
        return allocatedCapacity;
    }

    public void setAllocatedCapacity(Integer allocatedCapacity) {
        this.allocatedCapacity = allocatedCapacity;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<EmployeeSkillResponse> getSkills() {
        return skills;
    }

    public void setSkills(List<EmployeeSkillResponse> skills) {
        this.skills = skills;
    }
}
