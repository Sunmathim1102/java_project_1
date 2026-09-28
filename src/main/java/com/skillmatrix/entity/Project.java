package com.skillmatrix.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String projectCode;

    @Column(nullable = false, length = 100)
    private String projectName;

    @Column(length = 255)
    private String description;

    @Column(length = 100)
    private String clientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status = ProjectStatus.PLANNED;

    @Column(nullable = false)
    private Integer requiredTeamSize = 1;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectSkillRequirement> skillRequirements = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Allocation> allocations = new ArrayList<>();

    public Project() {}

    public Project(String projectCode, String projectName, String description, String clientName, ProjectStatus status, Integer requiredTeamSize) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.clientName = clientName;
        this.status = status != null ? status : ProjectStatus.PLANNED;
        this.requiredTeamSize = requiredTeamSize != null ? requiredTeamSize : 1;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = ProjectStatus.PLANNED;
        if (this.requiredTeamSize == null) this.requiredTeamSize = 1;
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

    public List<ProjectSkillRequirement> getSkillRequirements() {
        return skillRequirements;
    }

    public void setSkillRequirements(List<ProjectSkillRequirement> skillRequirements) {
        this.skillRequirements = skillRequirements;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }
}
