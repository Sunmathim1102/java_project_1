package com.skillmatrix.service;

import com.skillmatrix.model.EmployeeSkillRequest;
import com.skillmatrix.model.EmployeeSkillResponse;

import java.util.List;

public interface EmployeeSkillService {
    EmployeeSkillResponse assignSkillToEmployee(Long employeeId, EmployeeSkillRequest request);
    List<EmployeeSkillResponse> getSkillsByEmployeeId(Long employeeId);
    EmployeeSkillResponse updateEmployeeSkill(Long employeeId, Long skillId, EmployeeSkillRequest request);
    void removeSkillFromEmployee(Long employeeId, Long skillId);
}
