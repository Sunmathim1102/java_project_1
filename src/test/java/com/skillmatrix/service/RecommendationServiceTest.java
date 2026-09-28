package com.skillmatrix.service;

import com.skillmatrix.entity.*;
import com.skillmatrix.model.RecommendationResponse;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.repository.ProjectSkillRequirementRepository;
import com.skillmatrix.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectSkillRequirementRepository projectSkillRequirementRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Test
    void testRecommendationPrioritizesHigherProficiencyAndSkillMatch() {
        Project project = new Project("PRJ-001", "Banking", "Desc", "Client", ProjectStatus.ACTIVE, 2);
        project.setId(1L);

        Skill java = new Skill("Java", "Java Core", "Backend");
        java.setId(10L);

        ProjectSkillRequirement requirement = new ProjectSkillRequirement(project, java, 4, 100);

        // Employee A: Java 5, Available 30%
        Employee empA = new Employee("EMP1", "John", "john@test.com", "Dev", "Senior", 100);
        empA.setId(101L);
        empA.setAvailableCapacity(30);
        empA.setEmployeeSkills(List.of(new EmployeeSkill(empA, java, 5)));

        // Employee B: Java 4, Available 80%
        Employee empB = new Employee("EMP2", "Alice", "alice@test.com", "Dev", "Mid", 100);
        empB.setId(102L);
        empB.setAvailableCapacity(80);
        empB.setEmployeeSkills(List.of(new EmployeeSkill(empB, java, 4)));

        // Employee C: Java 2, Available 100% (Under-qualified)
        Employee empC = new Employee("EMP3", "David", "david@test.com", "Dev", "Junior", 100);
        empC.setId(103L);
        empC.setAvailableCapacity(100);
        empC.setEmployeeSkills(List.of(new EmployeeSkill(empC, java, 2)));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectSkillRequirementRepository.findByProjectId(1L)).thenReturn(List.of(requirement));
        when(employeeRepository.findAllActiveWithSkills()).thenReturn(Arrays.asList(empA, empB, empC));

        List<RecommendationResponse> recommendations = recommendationService.recommendEmployeesForProject(1L);

        assertNotNull(recommendations);
        assertEquals(3, recommendations.size());

        // Both John and Alice meet proficiency requirement (>= 4), David (L2) ranks lower due to under-qualification penalty
        assertTrue(recommendations.get(0).getOverallMatchScore() > recommendations.get(2).getOverallMatchScore());
        assertEquals("David", recommendations.get(2).getEmployeeName());
    }
}
