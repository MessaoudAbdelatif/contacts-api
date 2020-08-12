package com.abdelatif.contactsapi.controller;

import com.abdelatif.contactsapi.dto.SkillDto;
import com.abdelatif.contactsapi.service.SkillService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
@Slf4j
public class SkillController {

  private final SkillService skillService;


  @ApiOperation(value = "Get all the skills available")
  @GetMapping()
  public ResponseEntity<List<SkillDto>> getAllSkills() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(skillService.getAll());
  }

  @ApiOperation(value = "Get a skill by Id")
  @GetMapping("/{id}")
  public ResponseEntity<SkillDto> getSkillById(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(skillService.getSkill(id));
  }

  @ApiOperation(value = "Create new skill")
  @PostMapping()
  public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(skillService.save(skillDto));
  }

  @ApiOperation(value = "Create new skill for the current user")
  @PostMapping("/new-skill")
  public ResponseEntity<SkillDto> createSkillForCurrentContact(@RequestBody SkillDto skillDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(skillService.saveForCurrentContact(skillDto));
  }

  @ApiOperation(value = "Update an available skill -fetch by Id")
  @PutMapping("/{id}")
  public ResponseEntity<SkillDto> updateSkill(@PathVariable Long id,
      @RequestBody SkillDto skillDto) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(skillService.update(id, skillDto));
  }

  @ApiOperation(value = "Remove an available skill -fetch by Id")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSkill(@PathVariable Long id) {
    skillService.delete(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body("The Skill is removed successfully !");
  }
}
