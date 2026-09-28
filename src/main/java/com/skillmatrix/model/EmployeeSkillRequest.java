package com.skillmatrix.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EmployeeSkillRequest {

    @NotNull(message = "Skill ID is required")
    private Long skillId;

    @NotNull(message = "Proficiency level is required")
    @Min(value = 1, message = "Proficiency level must be at least 1 (Beginner)")
    @Max(value = 5, message = "Proficiency level must not exceed 5 (Expert)")
    private Integer proficiencyLevel;

    public EmployeeSkillRequest() {}

    public EmployeeSkillRequest(Long skillId, Integer proficiencyLevel) {
        this.skillId = skillId;
        this.proficiencyLevel = proficiencyLevel;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
