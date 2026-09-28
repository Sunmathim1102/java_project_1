package com.skillmatrix.model;

import com.skillmatrix.entity.AllocationStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AllocationRequest {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotNull(message = "Allocation percentage is required")
    @Min(value = 1, message = "Allocation percentage must be at least 1%")
    @Max(value = 100, message = "Allocation percentage cannot exceed 100%")
    private Integer allocationPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private AllocationStatus status = AllocationStatus.ACTIVE;

    public AllocationRequest() {}

    public AllocationRequest(Long employeeId, Long projectId, Integer allocationPercentage, LocalDate startDate, LocalDate endDate, AllocationStatus status) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.allocationPercentage = allocationPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status != null ? status : AllocationStatus.ACTIVE;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getAllocationPercentage() {
        return allocationPercentage;
    }

    public void setAllocationPercentage(Integer allocationPercentage) {
        this.allocationPercentage = allocationPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AllocationStatus getStatus() {
        return status;
    }

    public void setStatus(AllocationStatus status) {
        this.status = status;
    }
}
