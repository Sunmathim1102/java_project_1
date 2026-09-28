package com.skillmatrix.model;

public class ProjectSkillRequirementResponse {

    private Long id;
    private Long skillId;
    private String skillName;
    private String category;
    private Integer requiredProficiency;
    private String requiredProficiencyLabel;
    private Integer importancePercentage;

    public ProjectSkillRequirementResponse() {}

    public ProjectSkillRequirementResponse(Long id, Long skillId, String skillName, String category, Integer requiredProficiency, Integer importancePercentage) {
        this.id = id;
        this.skillId = skillId;
        this.skillName = skillName;
        this.category = category;
        this.requiredProficiency = requiredProficiency;
        this.requiredProficiencyLabel = EmployeeSkillResponse.resolveProficiencyLabel(requiredProficiency);
        this.importancePercentage = importancePercentage;
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

    public Integer getRequiredProficiency() {
        return requiredProficiency;
    }

    public void setRequiredProficiency(Integer requiredProficiency) {
        this.requiredProficiency = requiredProficiency;
        this.requiredProficiencyLabel = EmployeeSkillResponse.resolveProficiencyLabel(requiredProficiency);
    }

    public String getRequiredProficiencyLabel() {
        return requiredProficiencyLabel;
    }

    public void setRequiredProficiencyLabel(String requiredProficiencyLabel) {
        this.requiredProficiencyLabel = requiredProficiencyLabel;
    }

    public Integer getImportancePercentage() {
        return importancePercentage;
    }

    public void setImportancePercentage(Integer importancePercentage) {
        this.importancePercentage = importancePercentage;
    }
}
