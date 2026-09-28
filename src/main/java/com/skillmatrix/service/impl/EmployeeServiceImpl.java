package com.skillmatrix.service.impl;

import com.skillmatrix.entity.AllocationStatus;
import com.skillmatrix.entity.Employee;
import com.skillmatrix.exception.DuplicateResourceException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.EmployeeAvailabilityResponse;
import com.skillmatrix.model.EmployeeRequest;
import com.skillmatrix.model.EmployeeResponse;
import com.skillmatrix.repository.AllocationRepository;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.service.EmployeeService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AllocationRepository allocationRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AllocationRepository allocationRepository) {
        this.employeeRepository = employeeRepository;
        this.allocationRepository = allocationRepository;
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee with code " + request.getEmployeeCode() + " already exists");
        }
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee with email " + request.getEmail() + " already exists");
        }

        Employee employee = new Employee();
        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setTotalCapacity(request.getTotalCapacity() != null ? request.getTotalCapacity() : 100);
        employee.setAllocatedCapacity(0);
        employee.setAvailableCapacity(employee.getTotalCapacity());
        employee.setActive(request.getActive() != null ? request.getActive() : true);

        Employee saved = employeeRepository.save(employee);
        return EntityDtoMapper.toEmployeeResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EntityDtoMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return EntityDtoMapper.toEmployeeResponse(employee);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // Check unique code if changed
        if (!employee.getEmployeeCode().equalsIgnoreCase(request.getEmployeeCode()) &&
                employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee with code " + request.getEmployeeCode() + " already exists");
        }

        // Check unique email if changed
        if (!employee.getEmail().equalsIgnoreCase(request.getEmail()) &&
                employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Employee with email " + request.getEmail() + " already exists");
        }

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());

        if (request.getTotalCapacity() != null) {
            employee.setTotalCapacity(request.getTotalCapacity());
        }
        if (request.getActive() != null) {
            employee.setActive(request.getActive());
        }

        // Recalculate capacity based on active allocations
        Integer activeAllocated = allocationRepository.sumActiveAllocationPercentageByEmployeeId(id);
        employee.setAllocatedCapacity(activeAllocated != null ? activeAllocated : 0);
        employee.setAvailableCapacity(Math.max(0, employee.getTotalCapacity() - employee.getAllocatedCapacity()));

        Employee updated = employeeRepository.save(employee);
        return EntityDtoMapper.toEmployeeResponse(updated);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeAvailabilityResponse getEmployeeAvailability(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        var activeAllocations = allocationRepository.findByEmployeeIdAndStatus(employeeId, AllocationStatus.ACTIVE)
                .stream()
                .map(EntityDtoMapper::toAllocationResponse)
                .collect(Collectors.toList());

        EmployeeAvailabilityResponse resp = new EmployeeAvailabilityResponse();
        resp.setEmployeeId(employee.getId());
        resp.setEmployeeCode(employee.getEmployeeCode());
        resp.setName(employee.getName());
        resp.setDepartment(employee.getDepartment());
        resp.setDesignation(employee.getDesignation());
        resp.setTotalCapacity(employee.getTotalCapacity());
        resp.setAllocatedCapacity(employee.getAllocatedCapacity());
        resp.setAvailableCapacity(employee.getAvailableCapacity());
        resp.setActiveAllocations(activeAllocations);
        return resp;
    }

    @Override
    @Transactional
    public void recalculateEmployeeCapacity(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        Integer activeAllocated = allocationRepository.sumActiveAllocationPercentageByEmployeeId(employeeId);
        int allocated = activeAllocated != null ? activeAllocated : 0;
        employee.setAllocatedCapacity(allocated);
        employee.setAvailableCapacity(Math.max(0, employee.getTotalCapacity() - allocated));
        employeeRepository.save(employee);
    }
}
