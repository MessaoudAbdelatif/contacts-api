package com.abdelatif.contactsapi.controller;

import static com.abdelatif.contactsapi.model.Level.ADVANCED;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.abdelatif.contactsapi.dto.SkillDto;
import com.abdelatif.contactsapi.service.SkillService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(SkillController.class)
@Import(SkillController.class)
@Slf4j
class SkillControllerTest {

  SkillDto validSkill;

  @MockBean
  SkillService skillService;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    validSkill = SkillDto.builder()
        .id(1L)
        .name("Programming")
        .level(ADVANCED)
        .build();
  }

  @AfterEach
  void tearDown() {
    reset(skillService);
  }

  @Configuration
  @EnableWebSecurity  //  Isolating those tests from security aspects.
  static class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .csrf().disable()
          .authorizeRequests().anyRequest().anonymous();
    }
  }

  @Test
  void getAllSkills() throws Exception {

    List<SkillDto> skillDtoList = new ArrayList<>();
    skillDtoList.add(validSkill);

    given(skillService.getAll()).willReturn(skillDtoList);

    MvcResult result = mockMvc.perform(get("/api/skills"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", is(1)))
        .andExpect(jsonPath("$.[0].id", is(1)))
        .andExpect(jsonPath("$.[0].name", is("Programming")))
        .andExpect(jsonPath("$.[0].level", is("ADVANCED")))
        .andReturn();

    log.info("############## ------> " + result.getResponse().getContentAsString());
  }

  @Test
  void getSkillById() throws Exception {

    given(skillService.getSkill(1L)).willReturn(validSkill);

    MvcResult resultById = mockMvc.perform(get("/api/skills/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Programming")))
        .andExpect(jsonPath("$.level", is("ADVANCED")))
        .andReturn();

    log.info("############## ------> " + resultById.getResponse().getContentAsString());

  }

  @Test
  void createSkill() throws Exception {

    given(skillService.save(any())).willReturn(validSkill);

    MvcResult savedResult = mockMvc.perform(
        post("/api/skills").contentType(MediaType.APPLICATION_JSON)
            .content(" { \"name\": \"Programming\", \"level\": \"ADVANCED\" } ")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Programming")))
        .andExpect(jsonPath("$.level", is("ADVANCED")))
        .andReturn();

    log.info("############## ------> " + savedResult.getResponse().getContentAsString());
  }

  @Test
  void createSkillForCurrentContact() throws Exception {
    given(skillService.save(any())).willReturn(validSkill);

    MvcResult savedResult = mockMvc.perform(
        post("/api/skills").contentType(MediaType.APPLICATION_JSON)
            .content(" { \"name\": \"Programming\", \"level\": \"ADVANCED\" } ")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Programming")))
        .andExpect(jsonPath("$.level", is("ADVANCED")))
        .andReturn();

    log.info("############## ------> " + savedResult.getResponse().getContentAsString());
  }

  @Test
  void updateSkill() throws Exception {
    given(skillService.update(any(), any())).willReturn(validSkill);

    MvcResult updatedResult = mockMvc.perform(
        put("/api/skills/1").contentType(MediaType.APPLICATION_JSON)
            .content(" { \"name\": \"Programming\", \"level\": \"ADVANCED\" } ")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("Programming")))
        .andExpect(jsonPath("$.level", is("ADVANCED")))
        .andReturn();

    log.info("############## ------> " + updatedResult.getResponse().getContentAsString());
  }

  @Test
  void deleteSkill() throws Exception {
    MvcResult deletedResult = mockMvc.perform(
        delete("/api/skills/1"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", is("The Skill is removed successfully !")))
        .andReturn();

    log.info("############## ------> " + deletedResult.getResponse().getContentAsString());
  }
}