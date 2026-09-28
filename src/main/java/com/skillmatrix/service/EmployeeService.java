package com.skillmatrix.service;

import com.skillmatrix.model.EmployeeAvailabilityResponse;
import com.skillmatrix.model.EmployeeRequest;
import com.skillmatrix.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);
    void deleteEmployee(Long id);
    EmployeeAvailabilityResponse getEmployeeAvailability(Long employeeId);
    void recalculateEmployeeCapacity(Long employeeId);
}
