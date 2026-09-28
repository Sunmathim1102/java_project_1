package com.skillmatrix.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SkillRequest {

    @NotBlank(message = "Skill name is required")
    @Size(max = 100, message = "Skill name must be less than 100 characters")
    private String skillName;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    private Boolean active = true;

    public SkillRequest() {}

    public SkillRequest(String skillName, String description, String category) {
        this.skillName = skillName;
        this.description = description;
        this.category = category;
        this.active = true;
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
}
