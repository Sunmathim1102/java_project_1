package com.skillmatrix.model;

public class SkillResponse {

    private Long id;
    private String skillName;
    private String description;
    private String category;
    private Boolean active;

    public SkillResponse() {}

    public SkillResponse(Long id, String skillName, String description, String category, Boolean active) {
        this.id = id;
        this.skillName = skillName;
        this.description = description;
        this.category = category;
        this.active = active;
    }

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
}
