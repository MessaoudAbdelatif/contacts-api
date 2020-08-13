package com.abdelatif.contactsapi.controller;

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

import com.abdelatif.contactsapi.dto.ContactDto;
import com.abdelatif.contactsapi.service.implementation.ContactServiceImpl;
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

@WebMvcTest(ContactController.class)
@Import(ContactController.class)
@Slf4j
class ContactControllerTest {

  ContactDto validContact;

  @MockBean
  ContactServiceImpl contactService;

  @Autowired
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    validContact = ContactDto.builder()
        .id(1L)
        .firstname("John")
        .lastname("Snow")
        .address("Westeros")
        .email("sand@thenorth.com")
        .fullname("John Jr Snow")
        .mobileNumber("+41223438014")
        .build();
  }

  @AfterEach
  void tearDown() {
    reset(contactService);
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
  void createContact() throws Exception {
    given(contactService.save(any())).willReturn(validContact);

    MvcResult savedResult = mockMvc.perform(
        post("/api/contacts").contentType(MediaType.APPLICATION_JSON)
            .content(
                " { \"firstname\": \"John\", \"lastname\": \"Snow\", \"address\": \"Westeros\", \"email\": \"sand@thenorth.com\", \"fullname\": \"John Jr Snow\", \"mobileNumber\": \"+41223438014\"  } ")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.firstname", is("John")))
        .andExpect(jsonPath("$.address", is("Westeros")))
        .andReturn();

    log.info("############## ------> " + savedResult.getResponse().getContentAsString());
  }

  @Test
  void getAllContacts() throws Exception {

    List<ContactDto> contactDtoList = new ArrayList<>();
    contactDtoList.add(validContact);

    given(contactService.getAll()).willReturn(contactDtoList);

    MvcResult result = mockMvc.perform(get("/api/contacts"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", is(1)))
        .andExpect(jsonPath("$.[0].id", is(1)))
        .andExpect(jsonPath("$.[0].firstname", is("John")))
        .andExpect(jsonPath("$.[0].address", is("Westeros")))
        .andReturn();

    log.info("############## ------> " + result.getResponse().getContentAsString());

  }

  @Test
  void getContact() throws Exception {

    given(contactService.getContact(1L)).willReturn(validContact);

    MvcResult resultById = mockMvc.perform(get("/api/contacts/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.email", is("sand@thenorth.com")))
        .andExpect(jsonPath("$.mobileNumber", is("+41223438014")))
        .andReturn();

    log.info("############## ------> " + resultById.getResponse().getContentAsString());


  }

  @Test
  void updateContact() throws Exception {

    given(contactService.update(any(), any())).willReturn(validContact);

    MvcResult updatedResult = mockMvc.perform(
        put("/api/contacts/1").contentType(MediaType.APPLICATION_JSON)
            .content(
                " { \"firstname\": \"John\", \"lastname\": \"Snow\", \"address\": \"Westeros\", \"email\": \"sand@thenorth.com\", \"fullname\": \"John Jr Snow\", \"mobileNumber\": \"+41223438014\"  } ")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.email", is("sand@thenorth.com")))
        .andExpect(jsonPath("$.mobileNumber", is("+41223438014")))
        .andReturn();

    log.info("############## ------> " + updatedResult.getResponse().getContentAsString());
  }

  @Test
  void deleteContact() throws Exception {

    MvcResult deletedResult = mockMvc.perform(
        delete("/api/contacts/1"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", is("Contact information removed successfully !")))
        .andReturn();

    log.info("############## ------> " + deletedResult.getResponse().getContentAsString());

  }
}