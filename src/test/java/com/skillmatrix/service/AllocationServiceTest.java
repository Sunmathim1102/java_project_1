package com.skillmatrix.service;

import com.skillmatrix.entity.*;
import com.skillmatrix.exception.InsufficientCapacityException;
import com.skillmatrix.model.AllocationRequest;
import com.skillmatrix.model.AllocationResponse;
import com.skillmatrix.repository.AllocationRepository;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.repository.ProjectRepository;
import com.skillmatrix.service.impl.AllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AllocationServiceTest {

    @Mock
    private AllocationRepository allocationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private AllocationServiceImpl allocationService;

    private Employee employee;
    private Project project;

    @BeforeEach
    void setUp() {
        employee = new Employee("EMP101", "John Doe", "john@company.com", "Engineering", "Developer", 100);
        employee.setId(1L);
        employee.setAllocatedCapacity(60);
        employee.setAvailableCapacity(40);

        project = new Project("PRJ-001", "Banking Application", "Desc", "Client", ProjectStatus.ACTIVE, 3);
        project.setId(10L);
    }

    @Test
    void testSuccessfulAllocationWithinCapacity() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(allocationRepository.sumActiveAllocationPercentageByEmployeeId(1L)).thenReturn(60);

        Allocation savedAllocation = new Allocation(employee, project, 30, LocalDate.now(), null, AllocationStatus.ACTIVE);
        savedAllocation.setId(100L);
        when(allocationRepository.save(any(Allocation.class))).thenReturn(savedAllocation);

        AllocationRequest request = new AllocationRequest(1L, 10L, 30, LocalDate.now(), null, AllocationStatus.ACTIVE);
        AllocationResponse response = allocationService.createAllocation(request);

        assertNotNull(response);
        assertEquals(30, response.getAllocationPercentage());
        assertEquals(1L, response.getEmployeeId());
        assertEquals(10L, response.getProjectId());
    }

    @Test
    void testAllocationExceedingCapacityThrowsException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(projectRepository.findById(10L)).thenReturn(Optional.of(project));
        when(allocationRepository.sumActiveAllocationPercentageByEmployeeId(1L)).thenReturn(60);

        // Requested 50% when only 40% is available (60 + 50 = 110% > 100%)
        AllocationRequest request = new AllocationRequest(1L, 10L, 50, LocalDate.now(), null, AllocationStatus.ACTIVE);

        InsufficientCapacityException exception = assertThrows(
                InsufficientCapacityException.class,
                () -> allocationService.createAllocation(request)
        );

        assertTrue(exception.getMessage().contains("available capacity"));
        verify(allocationRepository, never()).save(any(Allocation.class));
    }
}
