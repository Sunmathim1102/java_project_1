package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Allocation;
import com.skillmatrix.entity.AllocationStatus;
import com.skillmatrix.entity.Employee;
import com.skillmatrix.entity.Project;
import com.skillmatrix.exception.InsufficientCapacityException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.AllocationRequest;
import com.skillmatrix.model.AllocationResponse;
import com.skillmatrix.repository.AllocationRepository;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.service.AllocationService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllocationServiceImpl implements AllocationService {

    private final AllocationRepository allocationRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public AllocationServiceImpl(AllocationRepository allocationRepository,
                                 EmployeeRepository employeeRepository,
                                 ProjectRepository projectRepository) {
        this.allocationRepository = allocationRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public AllocationResponse createAllocation(AllocationRequest request) {
        // 1. Find employee
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + request.getEmployeeId()));

        // 2. Find project
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));

        // 3. Calculate current active allocation for employee
        Integer currentAllocated = allocationRepository.sumActiveAllocationPercentageByEmployeeId(employee.getId());
        if (currentAllocated == null) currentAllocated = 0;

        int requestedPercentage = request.getAllocationPercentage();
        int totalCapacity = employee.getTotalCapacity() != null ? employee.getTotalCapacity() : 100;
        int availableCapacity = Math.max(0, totalCapacity - currentAllocated);

        // 4. Validate Capacity Business Rule: currentAllocated + requestedAllocation <= totalCapacity (100%)
        if (request.getStatus() == null || request.getStatus() == AllocationStatus.ACTIVE) {
            if (currentAllocated + requestedPercentage > totalCapacity) {
                throw new InsufficientCapacityException(
                        "Employee " + employee.getName() + " has only " + availableCapacity +
                                "% available capacity. Requested: " + requestedPercentage + "%."
                );
            }
        }

        Allocation allocation = new Allocation();
        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setAllocationPercentage(requestedPercentage);
        allocation.setStartDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now());
        allocation.setEndDate(request.getEndDate());
        allocation.setStatus(request.getStatus() != null ? request.getStatus() : AllocationStatus.ACTIVE);

        Allocation saved = allocationRepository.save(allocation);

        // Update employee capacity caches
        updateEmployeeCapacity(employee.getId());

        return EntityDtoMapper.toAllocationResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllocationResponse> getAllAllocations() {
        return allocationRepository.findAll().stream()
                .map(EntityDtoMapper::toAllocationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AllocationResponse getAllocationById(Long id) {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation not found with id: " + id));
        return EntityDtoMapper.toAllocationResponse(allocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllocationResponse> getAllocationsByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        return allocationRepository.findByEmployeeId(employeeId).stream()
                .map(EntityDtoMapper::toAllocationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AllocationResponse> getAllocationsByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with id: " + projectId);
        }
        return allocationRepository.findByProjectId(projectId).stream()
                .map(EntityDtoMapper::toAllocationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AllocationResponse updateAllocation(Long id, AllocationRequest request) {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation not found with id: " + id));

        Long employeeId = request.getEmployeeId() != null ? request.getEmployeeId() : allocation.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        Project project = allocation.getProject();
        if (request.getProjectId() != null && !request.getProjectId().equals(project.getId())) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + request.getProjectId()));
        }

        AllocationStatus newStatus = request.getStatus() != null ? request.getStatus() : allocation.getStatus();
        int requestedPercentage = request.getAllocationPercentage() != null ? request.getAllocationPercentage() : allocation.getAllocationPercentage();

        if (newStatus == AllocationStatus.ACTIVE) {
            Integer otherAllocations = allocationRepository.sumActiveAllocationPercentageByEmployeeIdExcluding(employeeId, id);
            if (otherAllocations == null) otherAllocations = 0;
            int totalCapacity = employee.getTotalCapacity() != null ? employee.getTotalCapacity() : 100;
            int availableCapacity = Math.max(0, totalCapacity - otherAllocations);

            if (otherAllocations + requestedPercentage > totalCapacity) {
                throw new InsufficientCapacityException(
                        "Employee " + employee.getName() + " has only " + availableCapacity +
                                "% available capacity. Requested: " + requestedPercentage + "%."
                );
            }
        }

        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setAllocationPercentage(requestedPercentage);
        if (request.getStartDate() != null) allocation.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) allocation.setEndDate(request.getEndDate());
        allocation.setStatus(newStatus);

        Allocation updated = allocationRepository.save(allocation);

        // Update employee capacity caches
        updateEmployeeCapacity(employee.getId());

        return EntityDtoMapper.toAllocationResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAllocation(Long id) {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation not found with id: " + id));

        Long employeeId = allocation.getEmployee().getId();
        allocationRepository.delete(allocation);

        updateEmployeeCapacity(employeeId);
    }

    private void updateEmployeeCapacity(Long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(emp -> {
            Integer activeAllocated = allocationRepository.sumActiveAllocationPercentageByEmployeeId(employeeId);
            int allocated = activeAllocated != null ? activeAllocated : 0;
            emp.setAllocatedCapacity(allocated);
            emp.setAvailableCapacity(Math.max(0, (emp.getTotalCapacity() != null ? emp.getTotalCapacity() : 100) - allocated));
            employeeRepository.save(emp);
        });
    }
}
