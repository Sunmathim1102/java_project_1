package com.skillmatrix.service;

import com.skillmatrix.model.ProjectSkillRequirementRequest;
import com.skillmatrix.model.ProjectSkillRequirementResponse;

import java.util.List;

public interface ProjectSkillRequirementService {
    ProjectSkillRequirementResponse addSkillRequirement(Long projectId, ProjectSkillRequirementRequest request);
    List<ProjectSkillRequirementResponse> getSkillRequirementsByProjectId(Long projectId);
    ProjectSkillRequirementResponse updateSkillRequirement(Long projectId, Long skillId, ProjectSkillRequirementRequest request);
    void removeSkillRequirement(Long projectId, Long skillId);
}
