package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Project;
import com.skillmatrix.entity.ProjectStatus;
import com.skillmatrix.exception.DuplicateResourceException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.ProjectRequest;
import com.skillmatrix.model.ProjectResponse;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.service.ProjectService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        if (projectRepository.existsByProjectCode(request.getProjectCode())) {
            throw new DuplicateResourceException("Project with code " + request.getProjectCode() + " already exists");
        }

        Project project = new Project();
        project.setProjectCode(request.getProjectCode());
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setClientName(request.getClientName());
        project.setStatus(request.getStatus() != null ? request.getStatus() : ProjectStatus.PLANNED);
        project.setRequiredTeamSize(request.getRequiredTeamSize() != null ? request.getRequiredTeamSize() : 1);

        Project saved = projectRepository.save(project);
        return EntityDtoMapper.toProjectResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(EntityDtoMapper::toProjectResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return EntityDtoMapper.toProjectResponse(project);
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        if (!project.getProjectCode().equalsIgnoreCase(request.getProjectCode()) &&
                projectRepository.existsByProjectCode(request.getProjectCode())) {
            throw new DuplicateResourceException("Project with code " + request.getProjectCode() + " already exists");
        }

        project.setProjectCode(request.getProjectCode());
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setClientName(request.getClientName());
        if (request.getStatus() != null) {
            project.setStatus(request.getStatus());
        }
        if (request.getRequiredTeamSize() != null) {
            project.setRequiredTeamSize(request.getRequiredTeamSize());
        }

        Project updated = projectRepository.save(project);
        return EntityDtoMapper.toProjectResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
