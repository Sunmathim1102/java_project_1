package com.skillmatrix.service;

import com.skillmatrix.model.ProjectRequest;
import com.skillmatrix.model.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(Long id);
    ProjectResponse updateProject(Long id, ProjectRequest request);
    void deleteProject(Long id);
}
