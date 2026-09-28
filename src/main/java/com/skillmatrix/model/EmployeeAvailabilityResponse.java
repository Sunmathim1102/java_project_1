package com.skillmatrix.model;

import java.util.List;

public class EmployeeAvailabilityResponse {

    private Long employeeId;
    private String employeeCode;
    private String name;
    private String department;
    private String designation;
    private Integer totalCapacity;
    private Integer allocatedCapacity;
    private Integer availableCapacity;
    private List<AllocationResponse> activeAllocations;

    public EmployeeAvailabilityResponse() {}

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public List<AllocationResponse> getActiveAllocations() {
        return activeAllocations;
    }

    public void setActiveAllocations(List<AllocationResponse> activeAllocations) {
        this.activeAllocations = activeAllocations;
    }
}
