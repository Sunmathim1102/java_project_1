package com.skillmatrix.controller;

import com.skillmatrix.model.ProjectSkillRequirementRequest;
import com.skillmatrix.model.ProjectSkillRequirementResponse;
import com.skillmatrix.service.ProjectSkillRequirementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/skills")
@Tag(name = "Project Skill Requirements", description = "Endpoints for defining project required skills and proficiency levels")
public class ProjectSkillRequirementController {

    private final ProjectSkillRequirementService projectSkillRequirementService;

    public ProjectSkillRequirementController(ProjectSkillRequirementService projectSkillRequirementService) {
        this.projectSkillRequirementService = projectSkillRequirementService;
    }

    @PostMapping
    @Operation(summary = "Add required skill to a project with proficiency level (1 to 5)")
    public ResponseEntity<ProjectSkillRequirementResponse> addSkillRequirement(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectSkillRequirementRequest request) {
        ProjectSkillRequirementResponse response = projectSkillRequirementService.addSkillRequirement(projectId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all skill requirements for a project")
    public ResponseEntity<List<ProjectSkillRequirementResponse>> getSkillRequirements(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectSkillRequirementService.getSkillRequirementsByProjectId(projectId));
    }

    @PutMapping("/{skillId}")
    @Operation(summary = "Update required skill proficiency or importance for a project")
    public ResponseEntity<ProjectSkillRequirementResponse> updateSkillRequirement(
            @PathVariable Long projectId,
            @PathVariable Long skillId,
            @Valid @RequestBody ProjectSkillRequirementRequest request) {
        return ResponseEntity.ok(projectSkillRequirementService.updateSkillRequirement(projectId, skillId, request));
    }

    @DeleteMapping("/{skillId}")
    @Operation(summary = "Remove required skill from a project")
    public ResponseEntity<Void> removeSkillRequirement(
            @PathVariable Long projectId,
            @PathVariable Long skillId) {
        projectSkillRequirementService.removeSkillRequirement(projectId, skillId);
        return ResponseEntity.noContent().build();
    }
}
