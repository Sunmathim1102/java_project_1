package com.skillmatrix.service;

import com.skillmatrix.model.AllocationRequest;
import com.skillmatrix.model.AllocationResponse;

import java.util.List;

public interface AllocationService {
    AllocationResponse createAllocation(AllocationRequest request);
    List<AllocationResponse> getAllAllocations();
    AllocationResponse getAllocationById(Long id);
    List<AllocationResponse> getAllocationsByEmployeeId(Long employeeId);
    List<AllocationResponse> getAllocationsByProjectId(Long projectId);
    AllocationResponse updateAllocation(Long id, AllocationRequest request);
    void deleteAllocation(Long id);
}
