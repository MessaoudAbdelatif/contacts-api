package com.abdelatif.contactsapi.repository;

import com.abdelatif.contactsapi.model.Contact;
import com.abdelatif.contactsapi.model.Level;
import com.abdelatif.contactsapi.model.Skill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SkillDao extends JpaRepository<Skill, Long> {
  List<Skill> findByContacts(Contact contact);
  Optional<Skill> findByNameAndLevel(String name, Level level);
}
