package com.skillmatrix.repository;

import com.skillmatrix.entity.Project;
import com.skillmatrix.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByProjectCode(String projectCode);

    boolean existsByProjectCode(String projectCode);

    List<Project> findByStatus(ProjectStatus status);
}
