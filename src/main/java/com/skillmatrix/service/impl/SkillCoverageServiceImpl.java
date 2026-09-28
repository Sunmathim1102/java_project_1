package com.skillmatrix.service.impl;

import com.skillmatrix.entity.EmployeeSkill;
import com.skillmatrix.entity.Project;
import com.skillmatrix.entity.ProjectSkillRequirement;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.ProjectSkillCoverageResponse;
import com.skillmatrix.model.SkillGapResponse;
import com.skillmatrix.repository.EmployeeSkillRepository;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.repository.ProjectSkillRequirementRepository;
import com.skillmatrix.service.SkillCoverageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillCoverageServiceImpl implements SkillCoverageService {

    private final ProjectRepository projectRepository;
    private final ProjectSkillRequirementRepository projectSkillRequirementRepository;
    private final EmployeeSkillRepository employeeSkillRepository;

    public SkillCoverageServiceImpl(ProjectRepository projectRepository,
                                    ProjectSkillRequirementRepository projectSkillRequirementRepository,
                                    EmployeeSkillRepository employeeSkillRepository) {
        this.projectRepository = projectRepository;
        this.projectSkillRequirementRepository = projectSkillRequirementRepository;
        this.employeeSkillRepository = employeeSkillRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectSkillCoverageResponse getSkillCoverageForProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        List<ProjectSkillRequirement> requirements = projectSkillRequirementRepository.findByProjectId(projectId);
        List<SkillGapResponse> details = new ArrayList<>();

        int fullyCoveredCount = 0;
        int gapCount = 0;

        for (ProjectSkillRequirement req : requirements) {
            Long skillId = req.getSkill().getId();
            int requiredProf = req.getRequiredProficiency();

            // Find all active employee skills for this skill
            List<EmployeeSkill> activeStaffSkills = employeeSkillRepository.findActiveBySkillId(skillId);

            int employeesWithSkill = activeStaffSkills.size();
            int employeesMeetingRequirement = 0;
            int highestProficiency = 0;
            int totalAvailableCapacity = 0;

            for (EmployeeSkill es : activeStaffSkills) {
                int empProf = es.getProficiencyLevel();
                if (empProf > highestProficiency) {
                    highestProficiency = empProf;
                }
                if (empProf >= requiredProf) {
                    employeesMeetingRequirement++;
                    if (es.getEmployee().getAvailableCapacity() != null) {
                        totalAvailableCapacity += es.getEmployee().getAvailableCapacity();
                    }
                }
            }

            SkillGapResponse gapResp = new SkillGapResponse();
            gapResp.setSkillId(skillId);
            gapResp.setSkillName(req.getSkill().getSkillName());
            gapResp.setCategory(req.getSkill().getCategory());
            gapResp.setRequiredProficiency(requiredProf);
            gapResp.setEmployeesWithSkill(employeesWithSkill);
            gapResp.setEmployeesMeetingRequirement(employeesMeetingRequirement);
            gapResp.setHighestAvailableProficiency(highestProficiency);
            gapResp.setTotalAvailableCapacity(totalAvailableCapacity);

            // Determine Coverage & Gap Status
            if (employeesMeetingRequirement > 0 && totalAvailableCapacity >= 100) {
                gapResp.setCoverageStatus("AVAILABLE");
                gapResp.setGapStatus("ADEQUATE_COVERAGE");
                fullyCoveredCount++;
            } else if (employeesMeetingRequirement > 0) {
                gapResp.setCoverageStatus("PARTIAL");
                gapResp.setGapStatus("LIMITED_AVAILABLE_CAPACITY");
                gapCount++;
            } else if (employeesWithSkill > 0) {
                gapResp.setCoverageStatus("GAP");
                gapResp.setGapStatus("PROFICIENCY_DEFICIT");
                gapCount++;
            } else {
                gapResp.setCoverageStatus("GAP");
                gapResp.setGapStatus("NO_SKILLED_STAFF");
                gapCount++;
            }

            details.add(gapResp);
        }

        ProjectSkillCoverageResponse response = new ProjectSkillCoverageResponse();
        response.setProjectId(project.getId());
        response.setProjectCode(project.getProjectCode());
        response.setProjectName(project.getProjectName());
        response.setTotalRequiredSkills(requirements.size());
        response.setFullyCoveredSkills(fullyCoveredCount);
        response.setGapSkills(gapCount);

        double coveragePct = requirements.isEmpty() ? 100.0 :
                Math.round(((double) fullyCoveredCount / requirements.size()) * 1000.0) / 10.0;
        response.setOverallCoveragePercentage(coveragePct);
        response.setSkillCoverageDetails(details);

        return response;
    }
}
