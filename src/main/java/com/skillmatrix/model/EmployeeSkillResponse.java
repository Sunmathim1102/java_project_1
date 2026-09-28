package com.skillmatrix.model;

public class EmployeeSkillResponse {

    private Long id;
    private Long skillId;
    private String skillName;
    private String category;
    private Integer proficiencyLevel;
    private String proficiencyLabel; // e.g., 1 = Beginner, 2 = Basic, 3 = Intermediate, 4 = Advanced, 5 = Expert

    public EmployeeSkillResponse() {}

    public EmployeeSkillResponse(Long id, Long skillId, String skillName, String category, Integer proficiencyLevel) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.category = category;
        this.proficiencyLevel = proficiencyLevel;
        this.proficiencyLabel = resolveProficiencyLabel(proficiencyLevel);
    }

    public static String resolveProficiencyLabel(Integer level) {
        if (level == null) return "Unknown";
        return switch (level) {
            case 1 -> "Beginner";
            case 2 -> "Basic";
            case 3 -> "Intermediate";
            case 4 -> "Advanced";
            case 5 -> "Expert";
            default -> "Level " + level;
        };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
        this.proficiencyLabel = resolveProficiencyLabel(proficiencyLevel);
    }

    public String getProficiencyLabel() {
        return proficiencyLabel;
    }

    public void setProficiencyLabel(String proficiencyLabel) {
        this.proficiencyLabel = proficiencyLabel;
    }
}
