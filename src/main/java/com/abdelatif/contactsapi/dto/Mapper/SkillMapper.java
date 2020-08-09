package com.abdelatif.contactsapi.dto.Mapper;

import com.abdelatif.contactsapi.dto.SkillDto;
import com.abdelatif.contactsapi.model.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

  Skill mapToSkill(SkillDto skillDto);

  SkillDto mapToSkillDto(Skill skill);

}
