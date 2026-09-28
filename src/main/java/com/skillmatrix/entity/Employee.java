package com.skillmatrix.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String employeeCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String designation;

    @Column(nullable = false)
    private Integer totalCapacity = 100;

    @Column(nullable = false)
    private Integer allocatedCapacity = 0;

    @Column(nullable = false)
    private Integer availableCapacity = 100;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeSkill> employeeSkills = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Allocation> allocations = new ArrayList<>();

    public Employee() {}

    public Employee(String employeeCode, String name, String email, String department, String designation, Integer totalCapacity) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.totalCapacity = totalCapacity != null ? totalCapacity : 100;
        this.allocatedCapacity = 0;
        this.availableCapacity = this.totalCapacity;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.totalCapacity == null) this.totalCapacity = 100;
        if (this.allocatedCapacity == null) this.allocatedCapacity = 0;
        if (this.availableCapacity == null) this.availableCapacity = this.totalCapacity - this.allocatedCapacity;
        if (this.active == null) this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

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

    public List<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(List<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }
}
