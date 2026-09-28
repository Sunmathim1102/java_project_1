package com.skillmatrix.controller;

import com.skillmatrix.model.EmployeeAvailabilityResponse;
import com.skillmatrix.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees/{employeeId}/availability")
@Tag(name = "Employee Availability", description = "Endpoints for checking employee workload, available capacity, and active project allocations")
public class EmployeeAvailabilityController {

    private final EmployeeService employeeService;

    public EmployeeAvailabilityController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get employee capacity breakdown (total, allocated, available) and active allocations")
    public ResponseEntity<EmployeeAvailabilityResponse> getEmployeeAvailability(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeAvailability(employeeId));
    }
}
