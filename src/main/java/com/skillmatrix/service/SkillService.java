package com.skillmatrix.service;

import com.skillmatrix.model.SkillRequest;
import com.skillmatrix.model.SkillResponse;

import java.util.List;

public interface SkillService {
    SkillResponse createSkill(SkillRequest request);
    List<SkillResponse> getAllSkills();
    SkillResponse getSkillById(Long id);
    SkillResponse updateSkill(Long id, SkillRequest request);
    void deleteSkill(Long id);
}
