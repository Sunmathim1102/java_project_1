package com.skillmatrix.controller;

import com.skillmatrix.model.RecommendationResponse;
import com.skillmatrix.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/recommendations")
@Tag(name = "Recommendation Engine", description = "Endpoints for intelligent employee recommendations ranked by skill match, proficiency levels, and current availability")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    @Operation(summary = "Get ranked employee staffing recommendations for a project based on skills, proficiency levels, and availability")
    public ResponseEntity<List<RecommendationResponse>> getProjectRecommendations(@PathVariable Long projectId) {
        return ResponseEntity.ok(recommendationService.recommendEmployeesForProject(projectId));
    }
}
