package com.skillmatrix.controller;

import com.skillmatrix.model.SkillRequest;
import com.skillmatrix.model.SkillResponse;
import com.skillmatrix.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@Tag(name = "Skills", description = "Endpoints for managing technical and domain skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @Operation(summary = "Create a new skill")
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody SkillRequest request) {
        SkillResponse response = skillService.createSkill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all skills")
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get skill by ID")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update skill details")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete skill")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
