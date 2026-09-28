package com.skillmatrix.service;

import com.skillmatrix.model.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    List<RecommendationResponse> recommendEmployeesForProject(Long projectId);
}
