package com.skillmatrix.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProjectSkillRequirementRequest {

    @NotNull(message = "Skill ID is required")
    private Long skillId;

    @NotNull(message = "Required proficiency is required")
    @Min(value = 1, message = "Required proficiency must be at least 1 (Beginner)")
    @Max(value = 5, message = "Required proficiency must not exceed 5 (Expert)")
    private Integer requiredProficiency;

    @Min(value = 1, message = "Importance percentage must be at least 1")
    @Max(value = 100, message = "Importance percentage cannot exceed 100")
    private Integer importancePercentage = 100;

    public ProjectSkillRequirementRequest() {}

    public ProjectSkillRequirementRequest(Long skillId, Integer requiredProficiency, Integer importancePercentage) {
        this.skillId = skillId;
        this.requiredProficiency = requiredProficiency;
        this.importancePercentage = importancePercentage != null ? importancePercentage : 100;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
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
