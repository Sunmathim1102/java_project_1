package com.skillmatrix.service.impl;

import com.skillmatrix.entity.Skill;
import com.skillmatrix.exception.DuplicateResourceException;
import com.skillmatrix.exception.ResourceNotFoundException;
import com.skillmatrix.model.SkillRequest;
import com.skillmatrix.model.SkillResponse;
import com.skillmatrix.repository.SkillRepository;
import com.skillmatrix.service.SkillService;
import com.skillmatrix.util.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional
    public SkillResponse createSkill(SkillRequest request) {
        if (skillRepository.existsBySkillNameIgnoreCase(request.getSkillName().trim())) {
            throw new DuplicateResourceException("Skill with name '" + request.getSkillName() + "' already exists");
        }

        Skill skill = new Skill();
        skill.setSkillName(request.getSkillName().trim());
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());
        skill.setActive(request.getActive() != null ? request.getActive() : true);

        Skill saved = skillRepository.save(skill);
        return EntityDtoMapper.toSkillResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(EntityDtoMapper::toSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
        return EntityDtoMapper.toSkillResponse(skill);
    }

    @Override
    @Transactional
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

        String newName = request.getSkillName().trim();
        if (!skill.getSkillName().equalsIgnoreCase(newName) &&
                skillRepository.existsBySkillNameIgnoreCase(newName)) {
            throw new DuplicateResourceException("Skill with name '" + newName + "' already exists");
        }

        skill.setSkillName(newName);
        skill.setDescription(request.getDescription());
        skill.setCategory(request.getCategory());
        if (request.getActive() != null) {
            skill.setActive(request.getActive());
        }

        Skill updated = skillRepository.save(skill);
        return EntityDtoMapper.toSkillResponse(updated);
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }
}
