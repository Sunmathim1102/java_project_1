package com.skillmatrix.model;

import java.util.List;

public class ProjectSkillCoverageResponse {

    private Long projectId;
    private String projectCode;
    private String projectName;
    private Integer totalRequiredSkills;
    private Integer fullyCoveredSkills;
    private Integer gapSkills;
    private Double overallCoveragePercentage;
    private List<SkillGapResponse> skillCoverageDetails;

    public ProjectSkillCoverageResponse() {}

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
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

    public Integer getTotalRequiredSkills() {
        return totalRequiredSkills;
    }

    public void setTotalRequiredSkills(Integer totalRequiredSkills) {
        this.totalRequiredSkills = totalRequiredSkills;
    }

    public Integer getFullyCoveredSkills() {
        return fullyCoveredSkills;
    }

    public void setFullyCoveredSkills(Integer fullyCoveredSkills) {
        this.fullyCoveredSkills = fullyCoveredSkills;
    }

    public Integer getGapSkills() {
        return gapSkills;
    }

    public void setGapSkills(Integer gapSkills) {
        this.gapSkills = gapSkills;
    }

    public Double getOverallCoveragePercentage() {
        return overallCoveragePercentage;
    }

    public void setOverallCoveragePercentage(Double overallCoveragePercentage) {
        this.overallCoveragePercentage = overallCoveragePercentage;
    }

    public List<SkillGapResponse> getSkillCoverageDetails() {
        return skillCoverageDetails;
    }

    public void setSkillCoverageDetails(List<SkillGapResponse> skillCoverageDetails) {
        this.skillCoverageDetails = skillCoverageDetails;
    }
}
