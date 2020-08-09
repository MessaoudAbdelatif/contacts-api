package com.abdelatif.contactsapi.service;

import com.abdelatif.contactsapi.dto.Mapper.SkillMapper;
import com.abdelatif.contactsapi.dto.SkillDto;
import com.abdelatif.contactsapi.model.Contact;
import com.abdelatif.contactsapi.model.Skill;
import com.abdelatif.contactsapi.model.UserApi;
import com.abdelatif.contactsapi.repository.SkillDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SkillService {

  private final SkillDao skillDao;
  private final SkillMapper skillMapper;
  private final AuthService authService;

  public SkillDto saveForCurrentContact(SkillDto skillDto) {
    Skill saveSkill = skillDao.save(skillMapper.mapToSkill(skillDto));
    UserApi currentUser = authService.getCurrentUser();
    Contact contact = currentUser.getContact();
    if (contact != null) {
      contact.getSkills().add(saveSkill);
    }
    return skillMapper.mapToSkillDto(saveSkill);
  }

  public SkillDto save(SkillDto skillDto) {
    Skill saveSkill = skillDao.save(skillMapper.mapToSkill(skillDto));
    return skillMapper.mapToSkillDto(saveSkill);
  }
}
