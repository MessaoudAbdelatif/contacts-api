package com.abdelatif.contactsapi.controller;

import com.abdelatif.contactsapi.model.Contact;
import com.abdelatif.contactsapi.service.SkillService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
@Slf4j
public class SkillController {

  private final SkillService skillService;

  @GetMapping()
  private void getAllSkillByContact(Contact contact){

  }
}
