package com.skillmatrix.model;

import com.skillmatrix.entity.ProjectStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponse {

    private Long id;
    private String projectCode;
    private String projectName;
    private String description;
    private String clientName;
    private ProjectStatus status;
    private Integer requiredTeamSize;
    private Integer activeAllocationsCount;
    private Integer totalAllocatedPercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProjectSkillRequirementResponse> skillRequirements;

    public ProjectResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Integer getRequiredTeamSize() {
        return requiredTeamSize;
    }

    public void setRequiredTeamSize(Integer requiredTeamSize) {
        this.requiredTeamSize = requiredTeamSize;
    }

    public Integer getActiveAllocationsCount() {
        return activeAllocationsCount;
    }

    public void setActiveAllocationsCount(Integer activeAllocationsCount) {
        this.activeAllocationsCount = activeAllocationsCount;
    }

    public Integer getTotalAllocatedPercentage() {
        return totalAllocatedPercentage;
    }

    public void setTotalAllocatedPercentage(Integer totalAllocatedPercentage) {
        this.totalAllocatedPercentage = totalAllocatedPercentage;
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

    public List<ProjectSkillRequirementResponse> getSkillRequirements() {
        return skillRequirements;
    }

    public void setSkillRequirements(List<ProjectSkillRequirementResponse> skillRequirements) {
        this.skillRequirements = skillRequirements;
    }
}
