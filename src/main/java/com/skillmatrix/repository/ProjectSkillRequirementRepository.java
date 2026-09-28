package com.skillmatrix.repository;

import com.skillmatrix.entity.ProjectSkillRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectSkillRequirementRepository extends JpaRepository<ProjectSkillRequirement, Long> {

    List<ProjectSkillRequirement> findByProjectId(Long projectId);

    Optional<ProjectSkillRequirement> findByProjectIdAndSkillId(Long projectId, Long skillId);

    boolean existsByProjectIdAndSkillId(Long projectId, Long skillId);

    void deleteByProjectIdAndSkillId(Long projectId, Long skillId);
}
