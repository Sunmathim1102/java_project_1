package com.skillmatrix.controller;

import com.skillmatrix.model.AllocationRequest;
import com.skillmatrix.model.AllocationResponse;
import com.skillmatrix.service.AllocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
@Tag(name = "Allocations", description = "Endpoints for managing employee project allocations and capacity constraints")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping
    @Operation(summary = "Create project allocation for employee (verifies employee total active allocation does not exceed 100%)")
    public ResponseEntity<AllocationResponse> createAllocation(@Valid @RequestBody AllocationRequest request) {
        AllocationResponse response = allocationService.createAllocation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all allocations (optionally filter by employeeId or projectId)")
    public ResponseEntity<List<AllocationResponse>> getAllAllocations(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) Long projectId) {
        if (employeeId != null) {
            return ResponseEntity.ok(allocationService.getAllocationsByEmployeeId(employeeId));
        } else if (projectId != null) {
            return ResponseEntity.ok(allocationService.getAllocationsByProjectId(projectId));
        }
        return ResponseEntity.ok(allocationService.getAllAllocations());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get allocation by ID")
    public ResponseEntity<AllocationResponse> getAllocationById(@PathVariable Long id) {
        return ResponseEntity.ok(allocationService.getAllocationById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update allocation details (capacity rule enforced)")
    public ResponseEntity<AllocationResponse> updateAllocation(@PathVariable Long id, @Valid @RequestBody AllocationRequest request) {
        return ResponseEntity.ok(allocationService.updateAllocation(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete allocation (restores employee available capacity)")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.noContent().build();
    }
}
