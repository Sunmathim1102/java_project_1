package com.skillmatrix.util;

import com.skillmatrix.entity.Employee;
import com.skillmatrix.entity.EmployeeSkill;
import com.skillmatrix.entity.ProjectSkillRequirement;
import com.skillmatrix.model.RecommendationResponse;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationScorer {

    public static final double SKILL_MATCH_WEIGHT = 0.40;
    public static final double PROFICIENCY_WEIGHT = 0.40;
    public static final double AVAILABILITY_WEIGHT = 0.20;

    /**
     * Calculates a comprehensive recommendation score and detailed evaluation for an employee against project requirements.
     * Higher proficiency employees are rewarded and rank higher for the same skills.
     */
    public static RecommendationResponse evaluateEmployee(Employee employee, List<ProjectSkillRequirement> requirements) {
        RecommendationResponse response = new RecommendationResponse();
        response.setEmployeeId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setEmployeeName(employee.getName());
        response.setDepartment(employee.getDepartment());
        response.setDesignation(employee.getDesignation());
        response.setAvailableCapacity(employee.getAvailableCapacity());
        response.setCurrentAllocatedCapacity(employee.getAllocatedCapacity());

        if (requirements == null || requirements.isEmpty()) {
            response.setOverallMatchScore(Math.round(employee.getAvailableCapacity() * 10.0) / 10.0);
            response.setSkillMatchPercentage(100.0);
            response.setAverageMatchedProficiency(0.0);
            response.setAverageRequiredProficiency(0.0);
            response.setMatchedSkills(Collections.emptyList());
            response.setMissingSkills(Collections.emptyList());
            response.setRecommendationReason("No specific skill requirements defined for project. Ranked by availability.");
            return response;
        }

        // Map employee skills by Skill ID
        Map<Long, EmployeeSkill> empSkillMap = new HashMap<>();
        if (employee.getEmployeeSkills() != null) {
            for (EmployeeSkill es : employee.getEmployeeSkills()) {
                empSkillMap.put(es.getSkill().getId(), es);
            }
        }

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        double totalProficiencyScore = 0.0;
        double sumMatchedProficiency = 0.0;
        double sumRequiredProficiency = 0.0;
        int matchedCount = 0;
        int totalRequirements = requirements.size();

        for (ProjectSkillRequirement req : requirements) {
            Long skillId = req.getSkill().getId();
            String skillName = req.getSkill().getSkillName();
            int requiredProf = req.getRequiredProficiency();
            sumRequiredProficiency += requiredProf;

            if (empSkillMap.containsKey(skillId)) {
                EmployeeSkill empSkill = empSkillMap.get(skillId);
                int empProf = empSkill.getProficiencyLevel();
                matchedSkills.add(skillName + " (L" + empProf + "/L" + requiredProf + ")");
                matchedCount++;
                sumMatchedProficiency += empProf;

                // Proficiency score calculation:
                // Scale base on employee proficiency vs required.
                // If empProf >= requiredProf, base 100 plus 5 points bonus per extra proficiency level up to 110.
                // If empProf < requiredProf, proportionally scaled (e.g. 2/4 = 50.0).
                double skillScore;
                if (empProf >= requiredProf) {
                    skillScore = 100.0 + (empProf - requiredProf) * 5.0;
                } else {
                    skillScore = ((double) empProf / requiredProf) * 80.0; // penalty for under-qualification
                }
                totalProficiencyScore += skillScore;
            } else {
                missingSkills.add(skillName + " (L" + requiredProf + ")");
            }
        }

        // 1. Skill Match Score (0 - 100)
        double skillMatchPercentage = ((double) matchedCount / totalRequirements) * 100.0;

        // 2. Average Normalized Proficiency Score across all required skills (0 - 110)
        double averageProficiencyScore = totalProficiencyScore / totalRequirements;

        // 3. Availability Score (0 - 100)
        double availabilityScore = employee.getAvailableCapacity() != null ? employee.getAvailableCapacity().doubleValue() : 0.0;

        // Overall Weighted Score
        double overallScore = (skillMatchPercentage * SKILL_MATCH_WEIGHT)
                + (averageProficiencyScore * PROFICIENCY_WEIGHT)
                + (availabilityScore * AVAILABILITY_WEIGHT);

        // Round to 1 decimal place
        overallScore = Math.round(overallScore * 10.0) / 10.0;
        skillMatchPercentage = Math.round(skillMatchPercentage * 10.0) / 10.0;

        double avgMatchedProf = matchedCount > 0 ? Math.round((sumMatchedProficiency / matchedCount) * 10.0) / 10.0 : 0.0;
        double avgReqProf = Math.round((sumRequiredProficiency / totalRequirements) * 10.0) / 10.0;

        response.setOverallMatchScore(overallScore);
        response.setSkillMatchPercentage(skillMatchPercentage);
        response.setAverageMatchedProficiency(avgMatchedProf);
        response.setAverageRequiredProficiency(avgReqProf);
        response.setMatchedSkills(matchedSkills);
        response.setMissingSkills(missingSkills);

        // Reason generation
        response.setRecommendationReason(generateReason(matchedCount, totalRequirements, avgMatchedProf, avgReqProf, employee.getAvailableCapacity()));

        return response;
    }

    private static String generateReason(int matched, int total, double avgMatchedProf, double avgReqProf, Integer availableCapacity) {
        if (matched == total && avgMatchedProf >= avgReqProf && availableCapacity >= 50) {
            return "Strong skill match with high proficiency and excellent available capacity";
        } else if (matched == total && availableCapacity > 0) {
            return "Complete skill match with available capacity (" + availableCapacity + "%)";
        } else if (matched > 0 && availableCapacity > 0) {
            return "Partial skill match (" + matched + "/" + total + " skills) with " + availableCapacity + "% availability";
        } else if (availableCapacity == 0) {
            return "Skill match available but employee is currently 100% allocated";
        } else {
            return "Low skill match for the project requirements";
        }
    }
}
