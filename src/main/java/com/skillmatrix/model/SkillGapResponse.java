package com.skillmatrix.model;

public class SkillGapResponse {

    private Long skillId;
    private String skillName;
    private String category;
    private Integer requiredProficiency;
    private String requiredProficiencyLabel;
    private Integer employeesWithSkill;
    private Integer employeesMeetingRequirement;
    private Integer highestAvailableProficiency;
    private String highestAvailableProficiencyLabel;
    private Integer totalAvailableCapacity; // e.g. 220%
    private String coverageStatus; // e.g. AVAILABLE, PARTIAL, GAP
    private String gapStatus; // e.g. "ADEQUATE_COVERAGE", "PROFICIENCY_DEFICIT", "NO_SKILLED_STAFF"

    public SkillGapResponse() {}

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

    public Integer getEmployeesWithSkill() {
        return employeesWithSkill;
    }

    public void setEmployeesWithSkill(Integer employeesWithSkill) {
        this.employeesWithSkill = employeesWithSkill;
    }

    public Integer getEmployeesMeetingRequirement() {
        return employeesMeetingRequirement;
    }

    public void setEmployeesMeetingRequirement(Integer employeesMeetingRequirement) {
        this.employeesMeetingRequirement = employeesMeetingRequirement;
    }

    public Integer getHighestAvailableProficiency() {
        return highestAvailableProficiency;
    }

    public void setHighestAvailableProficiency(Integer highestAvailableProficiency) {
        this.highestAvailableProficiency = highestAvailableProficiency;
        this.highestAvailableProficiencyLabel = highestAvailableProficiency != null && highestAvailableProficiency > 0
                ? EmployeeSkillResponse.resolveProficiencyLabel(highestAvailableProficiency)
                : "None";
    }

    public String getHighestAvailableProficiencyLabel() {
        return highestAvailableProficiencyLabel;
    }

    public void setHighestAvailableProficiencyLabel(String highestAvailableProficiencyLabel) {
        this.highestAvailableProficiencyLabel = highestAvailableProficiencyLabel;
    }

    public Integer getTotalAvailableCapacity() {
        return totalAvailableCapacity;
    }

    public void setTotalAvailableCapacity(Integer totalAvailableCapacity) {
        this.totalAvailableCapacity = totalAvailableCapacity;
    }

    public String getCoverageStatus() {
        return coverageStatus;
    }

    public void setCoverageStatus(String coverageStatus) {
        this.coverageStatus = coverageStatus;
    }

    public String getGapStatus() {
        return gapStatus;
    }

    public void setGapStatus(String gapStatus) {
        this.gapStatus = gapStatus;
    }
}
