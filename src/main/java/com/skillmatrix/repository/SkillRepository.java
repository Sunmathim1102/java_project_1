package com.skillmatrix.repository;

import com.skillmatrix.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findBySkillNameIgnoreCase(String skillName);

    boolean existsBySkillNameIgnoreCase(String skillName);

    List<Skill> findByActiveTrue();

    List<Skill> findByCategoryIgnoreCase(String category);
}
