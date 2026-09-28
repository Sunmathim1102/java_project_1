package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Project;
import com.skillmatrix.entity.ProjectSkillRequirement;
import com.skillmatrix.entity.Skill;
import com.skillmatrix.exception.DuplicateResourceException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.ProjectSkillRequirementRequest;
import com.skillmatrix.model.ProjectSkillRequirementResponse;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.repository.ProjectSkillRequirementRepository;
import com.skillmatrix.repository.SkillRepository;
import com.skillmatrix.service.ProjectSkillRequirementService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectSkillRequirementServiceImpl implements ProjectSkillRequirementService {

    private final ProjectSkillRequirementRepository projectSkillRequirementRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public ProjectSkillRequirementServiceImpl(ProjectSkillRequirementRepository projectSkillRequirementRepository,
                                             ProjectRepository projectRepository,
                                             SkillRepository skillRepository) {
        this.projectSkillRequirementRepository = projectSkillRequirementRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public ProjectSkillRequirementResponse addSkillRequirement(Long projectId, ProjectSkillRequirementRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + request.getSkillId()));

        if (projectSkillRequirementRepository.existsByProjectIdAndSkillId(projectId, request.getSkillId())) {
            throw new DuplicateResourceException("Skill requirement for '" + skill.getSkillName() + "' already exists on project '" + project.getProjectName() + "'. Use PUT to update.");
        }

        ProjectSkillRequirement requirement = new ProjectSkillRequirement();
        requirement.setProject(project);
        requirement.setSkill(skill);
        requirement.setRequiredProficiency(request.getRequiredProficiency());
        requirement.setImportancePercentage(request.getImportancePercentage() != null ? request.getImportancePercentage() : 100);

        ProjectSkillRequirement saved = projectSkillRequirementRepository.save(requirement);
        return EntityDtoMapper.toProjectSkillRequirementResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSkillRequirementResponse> getSkillRequirementsByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }
        return projectSkillRequirementRepository.findByProjectId(projectId).stream()
                .map(EntityDtoMapper::toProjectSkillRequirementResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectSkillRequirementResponse updateSkillRequirement(Long projectId, Long skillId, ProjectSkillRequirementRequest request) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }

        ProjectSkillRequirement requirement = projectSkillRequirementRepository.findByProjectIdAndSkillId(projectId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill requirement not found for project id " + projectId + " and skill id " + skillId));

        requirement.setRequiredProficiency(request.getRequiredProficiency());
        if (request.getImportancePercentage() != null) {
            requirement.setImportancePercentage(request.getImportancePercentage());
        }

        ProjectSkillRequirement updated = projectSkillRequirementRepository.save(requirement);
        return EntityDtoMapper.toProjectSkillRequirementResponse(updated);
    }

    @Override
    @Transactional
    public void removeSkillRequirement(Long projectId, Long skillId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }

        ProjectSkillRequirement requirement = projectSkillRequirementRepository.findByProjectIdAndSkillId(projectId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill requirement not found for project id " + projectId + " and skill id " + skillId));

        projectSkillRequirementRepository.delete(requirement);
    }
}
