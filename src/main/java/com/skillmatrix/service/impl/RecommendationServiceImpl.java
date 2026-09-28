package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Employee;
import com.skillmatrix.entity.Project;
import com.skillmatrix.entity.ProjectSkillRequirement;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.RecommendationResponse;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.repository.ProjectSkillRequirementRepository;
import com.skillmatrix.service.RecommendationService;
import com.skillmatrix.util.RecommendationScorer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final ProjectRepository projectRepository;
    private final ProjectSkillRequirementRepository projectSkillRequirementRepository;
    private final EmployeeRepository employeeRepository;

    public RecommendationServiceImpl(ProjectRepository projectRepository,
                                      ProjectSkillRequirementRepository projectSkillRequirementRepository,
                                      EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.projectSkillRequirementRepository = projectSkillRequirementRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendationResponse> recommendEmployeesForProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        List<ProjectSkillRequirement> requirements = projectSkillRequirementRepository.findByProjectId(projectId);
        List<Employee> activeEmployees = employeeRepository.findAllActiveWithSkills();

        return activeEmployees.stream()
                .map(employee -> RecommendationScorer.evaluateEmployee(employee, requirements))
                .sorted(Comparator.comparing(RecommendationResponse::getOverallMatchScore)
                        .thenComparing(RecommendationResponse::getSkillMatchPercentage)
                        .thenComparing(RecommendationResponse::getAverageMatchedProficiency)
                        .thenComparing(RecommendationResponse::getAvailableCapacity)
                        .reversed())
                .collect(Collectors.toList());
    }
}
