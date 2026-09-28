package com.skillmatrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillmatrix.entity.AllocationStatus;
import com.skillmatrix.entity.ProjectStatus;
import com.skillmatrix.model.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationWorkflowTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void testGetEmployeesList() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @Order(2)
    void testGetSkillsList() throws Exception {
        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(8))));
    }

    @Test
    @Order(3)
    void testGetProjectRecommendations() throws Exception {
        // Project 1 is seeded with Java, Spring Boot, MySQL, REST API requirements
        mockMvc.perform(get("/api/projects/1/recommendations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].overallMatchScore").isNumber())
                .andExpect(jsonPath("$[0].matchedSkills").isArray());
    }

    @Test
    @Order(4)
    void testGetSkillCoverage() throws Exception {
        mockMvc.perform(get("/api/projects/1/skill-coverage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.skillCoverageDetails").isArray());
    }

    @Test
    @Order(5)
    void testCapacityConstraintRejection() throws Exception {
        // John (Employee 1) is already allocated 60% with 40% remaining available capacity.
        // Attempting to allocate 50% should be rejected with 400 Bad Request
        AllocationRequest request = new AllocationRequest(1L, 1L, 50, null, null, AllocationStatus.ACTIVE);

        mockMvc.perform(post("/api/allocations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Allocation exceeds employee capacity"))
                .andExpect(jsonPath("$.message", containsString("available capacity")));
    }

    @Test
    @Order(6)
    void testEmployeeAvailabilityEndpoint() throws Exception {
        mockMvc.perform(get("/api/employees/1/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.totalCapacity").value(100))
                .andExpect(jsonPath("$.allocatedCapacity").value(60))
                .andExpect(jsonPath("$.availableCapacity").value(40));
    }
}
