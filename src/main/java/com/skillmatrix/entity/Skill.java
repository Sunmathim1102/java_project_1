package com.skillmatrix.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String skillName;

    @Column(length = 255)
    private String description;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeSkill> employeeSkills = new ArrayList<>();

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectSkillRequirement> projectSkillRequirements = new ArrayList<>();

    public Skill() {}

    public Skill(String skillName, String description, String category) {
        this.skillName = skillName;
        this.description = description;
        this.category = category;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        if (this.active == null) this.active = true;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(List<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public List<ProjectSkillRequirement> getProjectSkillRequirements() {
        return projectSkillRequirements;
    }

    public void setProjectSkillRequirements(List<ProjectSkillRequirement> projectSkillRequirements) {
        this.projectSkillRequirements = projectSkillRequirements;
    }
}
