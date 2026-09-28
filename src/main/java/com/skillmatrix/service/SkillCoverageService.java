package com.skillmatrix.service;

import com.skillmatrix.model.ProjectSkillCoverageResponse;

public interface SkillCoverageService {
    ProjectSkillCoverageResponse getSkillCoverageForProject(Long projectId);
}
