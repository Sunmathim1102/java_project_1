package com.skillmatrix.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "project_skill_requirements", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"project_id", "skill_id"})
})
public class ProjectSkillRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private Integer requiredProficiency; // Scale 1 to 5

    @Column(nullable = false)
    private Integer importancePercentage = 100;

    public ProjectSkillRequirement() {}

    public ProjectSkillRequirement(Project project, Skill skill, Integer requiredProficiency, Integer importancePercentage) {
        this.project = project;
        this.skill = skill;
        this.requiredProficiency = requiredProficiency;
        this.importancePercentage = importancePercentage != null ? importancePercentage : 100;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getRequiredProficiency() {
        return requiredProficiency;
    }

    public void setRequiredProficiency(Integer requiredProficiency) {
        this.requiredProficiency = requiredProficiency;
    }

    public Integer getImportancePercentage() {
        return importancePercentage;
    }

    public void setImportancePercentage(Integer importancePercentage) {
        this.importancePercentage = importancePercentage;
    }
}
