package com.abdelatif.contactsapi.repository;

import com.abdelatif.contactsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SkillDao extends JpaRepository<Skill, Long> {

}
