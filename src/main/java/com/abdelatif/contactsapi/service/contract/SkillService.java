package com.abdelatif.contactsapi.service.contract;

import com.abdelatif.contactsapi.dto.SkillDto;
import java.util.List;

/**
 * Skill Service a simple Skill CRUD interface.
 * */
public interface SkillService {

  SkillDto saveForCurrentContact(SkillDto skillDto);

  SkillDto save(SkillDto skillDto);

  List<SkillDto> getAll();

  SkillDto getSkill(Long id);

  SkillDto update(Long id, SkillDto skillDto);

  void delete(Long id);
}
