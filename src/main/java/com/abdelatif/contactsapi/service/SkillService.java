package com.abdelatif.contactsapi.service;

import com.abdelatif.contactsapi.dto.Mapper.SkillMapper;
import com.abdelatif.contactsapi.dto.SkillDto;
import com.abdelatif.contactsapi.exception.ContactApiException;
import com.abdelatif.contactsapi.model.Contact;
import com.abdelatif.contactsapi.model.Skill;
import com.abdelatif.contactsapi.model.UserApi;
import com.abdelatif.contactsapi.repository.SkillDao;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class SkillService {

  private final SkillDao skillDao;
  private final SkillMapper skillMapper;
  private final AuthService authService;

  @Transactional
  public SkillDto saveForCurrentContact(SkillDto skillDto) {
    Skill saveSkill = skillDao.save(skillMapper.mapToSkill(skillDto));
    UserApi currentUser = authService.getCurrentUser();
    Contact contact = currentUser.getContact();
    if (contact != null) {
      contact.getSkills().add(saveSkill);
    }
    return skillMapper.mapToSkillDto(saveSkill);
  }

  @Transactional
  public SkillDto save(SkillDto skillDto) {
    Skill saveSkill = skillDao.save(skillMapper.mapToSkill(skillDto));
    return skillMapper.mapToSkillDto(saveSkill);
  }

  @Transactional(readOnly = true)
  public List<SkillDto> getAll() {
    return skillDao.findAll()
        .stream()
        .map(skillMapper::mapToSkillDto)
        .collect(Collectors.toList());
  }

  public SkillDto getSkill(Long id) {
    Skill skill = skillDao.findById(id)
        .orElseThrow(() -> new ContactApiException("No skill found with this ID : " + id));
    return skillMapper.mapToSkillDto(skill);
  }

  @Transactional
  public SkillDto update(Long id, SkillDto skillDto) {
    Optional<Skill> skill = skillDao.findById(id);
    if (skill.isPresent()) {
      skillDto.setId(id);
      skillDao.save(skillMapper.mapToSkill(skillDto));
    } else {
      throw new ContactApiException("No skill found with this ID : " + id);
    }
    return skillDto;
  }

  @Transactional
  public void delete(Long id) {
    Skill skill = skillDao.findById(id).orElseThrow(
        () -> new ContactApiException("Can not delete ! no skill found with this ID : " + id));
    skillDao.delete(skill);
  }
}
