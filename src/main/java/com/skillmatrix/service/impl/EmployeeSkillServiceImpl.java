package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Employee;
import com.skillmatrix.entity.EmployeeSkill;
import com.skillmatrix.entity.Skill;
import com.skillmatrix.exception.DuplicateResourceException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.EmployeeSkillRequest;
import com.skillmatrix.model.EmployeeSkillResponse;
import com.skillmatrix.repository.EmployeeRepository;
import com.skillmatrix.repository.EmployeeSkillRepository;
import com.skillmatrix.repository.SkillRepository;
import com.skillmatrix.service.EmployeeSkillService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    private final EmployeeSkillRepository employeeSkillRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;

    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository,
                                   EmployeeRepository employeeRepository,
                                   SkillRepository skillRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public EmployeeSkillResponse assignSkillToEmployee(Long employeeId, EmployeeSkillRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + request.getSkillId()));

        if (employeeSkillRepository.existsByEmployeeIdAndSkillId(employeeId, request.getSkillId())) {
            throw new DuplicateResourceException("Skill '" + skill.getSkillName() + "' is already assigned to employee '" + employee.getName() + "'. Use PUT to update proficiency.");
        }

        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkill.setProficiencyLevel(request.getProficiencyLevel());

        EmployeeSkill saved = employeeSkillRepository.save(employeeSkill);
        return EntityDtoMapper.toEmployeeSkillResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSkillResponse> getSkillsByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        return employeeSkillRepository.findByEmployeeId(employeeId).stream()
                .map(EntityDtoMapper::toEmployeeSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeSkillResponse updateEmployeeSkill(Long employeeId, Long skillId, EmployeeSkillRequest request) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }

        EmployeeSkill employeeSkill = employeeSkillRepository.findByEmployeeIdAndSkillId(employeeId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill mapping not found for employee id " + employeeId + " and skill id " + skillId));

        employeeSkill.setProficiencyLevel(request.getProficiencyLevel());
        EmployeeSkill updated = employeeSkillRepository.save(employeeSkill);
        return EntityDtoMapper.toEmployeeSkillResponse(updated);
    }

    @Override
    @Transactional
    public void removeSkillFromEmployee(Long employeeId, Long skillId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }

        EmployeeSkill employeeSkill = employeeSkillRepository.findByEmployeeIdAndSkillId(employeeId, skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill mapping not found for employee id " + employeeId + " and skill id " + skillId));

        employeeSkillRepository.delete(employeeSkill);
    }
}
