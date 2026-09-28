package com.skillmatrix.model;

import com.skillmatrix.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

    @NotBlank(message = "Project code is required")
    @Size(max = 50, message = "Project code must be less than 50 characters")
    private String projectCode;

    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must be less than 100 characters")
    private String projectName;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 100, message = "Client name must be less than 100 characters")
    private String clientName;

    private ProjectStatus status = ProjectStatus.PLANNED;

    @NotNull(message = "Required team size is required")
    @Positive(message = "Required team size must be positive")
    private Integer requiredTeamSize = 1;

    public ProjectRequest() {}

    public ProjectRequest(String projectCode, String projectName, String description, String clientName, ProjectStatus status, Integer requiredTeamSize) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.clientName = clientName;
        this.status = status != null ? status : ProjectStatus.PLANNED;
        this.requiredTeamSize = requiredTeamSize != null ? requiredTeamSize : 1;
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
}
