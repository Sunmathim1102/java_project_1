package com.skillmatrix.controller;

import com.skillmatrix.model.ProjectSkillCoverageResponse;
import com.skillmatrix.service.SkillCoverageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects/{projectId}/skill-coverage")
@Tag(name = "Team Skill Coverage & Gaps", description = "Endpoints for analyzing team skill availability and identifying skill gaps for project requirements")
public class SkillCoverageController {

    private final SkillCoverageService skillCoverageService;

    public SkillCoverageController(SkillCoverageService skillCoverageService) {
        this.skillCoverageService = skillCoverageService;
    }

    @GetMapping
    @Operation(summary = "Get team-wide skill coverage and gap analysis for a project's required skill set")
    public ResponseEntity<ProjectSkillCoverageResponse> getSkillCoverage(@PathVariable Long projectId) {
        return ResponseEntity.ok(skillCoverageService.getSkillCoverageForProject(projectId));
    }
}
