package com.skillmatrix.model;

import jakarta.validation.constraints.*;

public class EmployeeRequest {

    @NotBlank(message = "Employee code is required")
    @Size(max = 50, message = "Employee code must be less than 50 characters")
    private String employeeCode;

    @NotBlank(message = "Employee name is required")
    @Size(max = 100, message = "Employee name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Size(max = 100, message = "Department must be less than 100 characters")
    private String department;

    @Size(max = 100, message = "Designation must be less than 100 characters")
    private String designation;

    @NotNull(message = "Total capacity is required")
    @Min(value = 0, message = "Total capacity cannot be less than 0")
    @Max(value = 100, message = "Total capacity cannot exceed 100")
    private Integer totalCapacity = 100;

    private Boolean active = true;

    public EmployeeRequest() {}

    public EmployeeRequest(String employeeCode, String name, String email, String department, String designation, Integer totalCapacity) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.totalCapacity = totalCapacity;
        this.active = true;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
