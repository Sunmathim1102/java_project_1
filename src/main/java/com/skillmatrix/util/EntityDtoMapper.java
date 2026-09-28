package com.skillmatrix.util;

import com.skillmatrix.entity.*;
import com.skillmatrix.model.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDtoMapper {

    public static EmployeeResponse toEmployeeResponse(Employee employee) {
        if (employee == null) return null;
        EmployeeResponse resp = new EmployeeResponse();
        resp.setId(employee.getId());
        resp.setEmployeeCode(employee.getEmployeeCode());
        resp.setName(employee.getName());
        resp.setEmail(employee.getEmail());
        resp.setDepartment(employee.getDepartment());
        resp.setDesignation(employee.getDesignation());
        resp.setTotalCapacity(employee.getTotalCapacity());
        resp.setAllocatedCapacity(employee.getAllocatedCapacity());
        resp.setAvailableCapacity(employee.getAvailableCapacity());
        resp.setActive(employee.getActive());
        resp.setCreatedAt(employee.getCreatedAt());
        resp.setUpdatedAt(employee.getUpdatedAt());

        if (employee.getEmployeeSkills() != null) {
            resp.setSkills(employee.getEmployeeSkills().stream()
                    .map(EntityDtoMapper::toEmployeeSkillResponse)
                    .collect(Collectors.toList()));
        } else {
            resp.setSkills(Collections.emptyList());
        }
        return resp;
    }

    public static SkillResponse toSkillResponse(Skill skill) {
        if (skill == null) return null;
        return new SkillResponse(
                skill.getId(),
                skill.getSkillName(),
                skill.getDescription(),
                skill.getCategory(),
                skill.getActive()
        );
    }

    public static EmployeeSkillResponse toEmployeeSkillResponse(EmployeeSkill employeeSkill) {
        if (employeeSkill == null) return null;
        return new EmployeeSkillResponse(
                employeeSkill.getId(),
                employeeSkill.getSkill() != null ? employeeSkill.getSkill().getId() : null,
                employeeSkill.getSkill() != null ? employeeSkill.getSkill().getSkillName() : null,
                employeeSkill.getSkill() != null ? employeeSkill.getSkill().getCategory() : null,
                employeeSkill.getProficiencyLevel()
        );
    }

    public static ProjectResponse toProjectResponse(Project project) {
        if (project == null) return null;
        ProjectResponse resp = new ProjectResponse();
        resp.setId(project.getId());
        resp.setProjectCode(project.getProjectCode());
        resp.setProjectName(project.getProjectName());
        resp.setDescription(project.getDescription());
        resp.setClientName(project.getClientName());
        resp.setStatus(project.getStatus());
        resp.setRequiredTeamSize(project.getRequiredTeamSize());
        resp.setCreatedAt(project.getCreatedAt());
        resp.setUpdatedAt(project.getUpdatedAt());

        if (project.getSkillRequirements() != null) {
            resp.setSkillRequirements(project.getSkillRequirements().stream()
                    .map(EntityDtoMapper::toProjectSkillRequirementResponse)
                    .collect(Collectors.toList()));
        } else {
            resp.setSkillRequirements(Collections.emptyList());
        }

        if (project.getAllocations() != null) {
            long activeCount = project.getAllocations().stream()
                    .filter(a -> a.getStatus() == AllocationStatus.ACTIVE)
                    .count();
            int totalAllocPct = project.getAllocations().stream()
                    .filter(a -> a.getStatus() == AllocationStatus.ACTIVE)
                    .mapToInt(Allocation::getAllocationPercentage)
                    .sum();
            resp.setActiveAllocationsCount((int) activeCount);
            resp.setTotalAllocatedPercentage(totalAllocPct);
        } else {
            resp.setActiveAllocationsCount(0);
            resp.setTotalAllocatedPercentage(0);
        }

        return resp;
    }

    public static ProjectSkillRequirementResponse toProjectSkillRequirementResponse(ProjectSkillRequirement req) {
        if (req == null) return null;
        return new ProjectSkillRequirementResponse(
                req.getId(),
                req.getSkill() != null ? req.getSkill().getId() : null,
                req.getSkill() != null ? req.getSkill().getSkillName() : null,
                req.getSkill() != null ? req.getSkill().getCategory() : null,
                req.getRequiredProficiency(),
                req.getImportancePercentage()
        );
    }

    public static AllocationResponse toAllocationResponse(Allocation allocation) {
        if (allocation == null) return null;
        AllocationResponse resp = new AllocationResponse();
        resp.setId(allocation.getId());
        if (allocation.getEmployee() != null) {
            resp.setEmployeeId(allocation.getEmployee().getId());
            resp.setEmployeeCode(allocation.getEmployee().getEmployeeCode());
            resp.setEmployeeName(allocation.getEmployee().getName());
        }
        if (allocation.getProject() != null) {
            resp.setProjectId(allocation.getProject().getId());
            resp.setProjectCode(allocation.getProject().getProjectCode());
            resp.setProjectName(allocation.getProject().getProjectName());
        }
        resp.setAllocationPercentage(allocation.getAllocationPercentage());
        resp.setStartDate(allocation.getStartDate());
        resp.setEndDate(allocation.getEndDate());
        resp.setStatus(allocation.getStatus());
        resp.setCreatedAt(allocation.getCreatedAt());
        return resp;
    }
}
