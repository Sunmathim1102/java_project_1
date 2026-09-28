package com.skillmatrix.controller;

import com.skillmatrix.model.EmployeeSkillRequest;
import com.skillmatrix.model.EmployeeSkillResponse;
import com.skillmatrix.service.EmployeeSkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees/{employeeId}/skills")
@Tag(name = "Employee Skills", description = "Endpoints for managing employee skill proficiencies (Scale 1 to 5)")
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    public EmployeeSkillController(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }

    @PostMapping
    @Operation(summary = "Assign a skill with proficiency level (1=Beginner, 2=Basic, 3=Intermediate, 4=Advanced, 5=Expert) to an employee")
    public ResponseEntity<EmployeeSkillResponse> assignSkill(
            @PathVariable Long employeeId,
            @Valid @RequestBody EmployeeSkillRequest request) {
        EmployeeSkillResponse response = employeeSkillService.assignSkillToEmployee(employeeId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all skills assigned to an employee")
    public ResponseEntity<List<EmployeeSkillResponse>> getEmployeeSkills(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeSkillService.getSkillsByEmployeeId(employeeId));
    }

    @PutMapping("/{skillId}")
    @Operation(summary = "Update employee proficiency level for a skill")
    public ResponseEntity<EmployeeSkillResponse> updateEmployeeSkill(
            @PathVariable Long employeeId,
            @PathVariable Long skillId,
            @Valid @RequestBody EmployeeSkillRequest request) {
        return ResponseEntity.ok(employeeSkillService.updateEmployeeSkill(employeeId, skillId, request));
    }

    @DeleteMapping("/{skillId}")
    @Operation(summary = "Remove a skill from an employee")
    public ResponseEntity<Void> removeSkill(
            @PathVariable Long employeeId,
            @PathVariable Long skillId) {
        employeeSkillService.removeSkillFromEmployee(employeeId, skillId);
        return ResponseEntity.noContent().build();
    }
}
