package com.skillmatrix.model;

import java.util.List;

public class RecommendationResponse {

    private Long employeeId;
    private String employeeCode;
    private String employeeName;
    private String department;
    private String designation;
    private Double overallMatchScore;
    private Double skillMatchPercentage;
    private Double averageMatchedProficiency;
    private Double averageRequiredProficiency;
    private Integer availableCapacity;
    private Integer currentAllocatedCapacity;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String recommendationReason;

    public RecommendationResponse() {}

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getOverallMatchScore() {
        return overallMatchScore;
    }

    public void setOverallMatchScore(Double overallMatchScore) {
        this.overallMatchScore = overallMatchScore;
    }

    public Double getSkillMatchPercentage() {
        return skillMatchPercentage;
    }

    public void setSkillMatchPercentage(Double skillMatchPercentage) {
        this.skillMatchPercentage = skillMatchPercentage;
    }

    public Double getAverageMatchedProficiency() {
        return averageMatchedProficiency;
    }

    public void setAverageMatchedProficiency(Double averageMatchedProficiency) {
        this.averageMatchedProficiency = averageMatchedProficiency;
    }

    public Double getAverageRequiredProficiency() {
        return averageRequiredProficiency;
    }

    public void setAverageRequiredProficiency(Double averageRequiredProficiency) {
        this.averageRequiredProficiency = averageRequiredProficiency;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public Integer getCurrentAllocatedCapacity() {
        return currentAllocatedCapacity;
    }

    public void setCurrentAllocatedCapacity(Integer currentAllocatedCapacity) {
        this.currentAllocatedCapacity = currentAllocatedCapacity;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }
}
