package com.abdelatif.contactsapi.controller;

import com.abdelatif.contactsapi.dto.ContactDto;
import com.abdelatif.contactsapi.service.implementation.ContactServiceImpl;
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
@RequestMapping("/api/contacts")
@AllArgsConstructor
@Slf4j
public class ContactController {

  private final ContactServiceImpl contactService;

  @ApiOperation(value = "Create new contact")
  @PostMapping
  public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(contactService.save(contactDto));
  }

  @ApiOperation(value = "Get all the contacts")
  @GetMapping
  public ResponseEntity<List<ContactDto>> getAllContacts() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contactService.getAll());
  }

  @ApiOperation(value = "Get a contact by Id")
  @GetMapping("/{id}")
  public ResponseEntity<ContactDto> getContact(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contactService.getContact(id));

  }

  @ApiOperation(value = "Update an available contact -fetch by Id")
  @PutMapping("/{id}")
  public ResponseEntity<ContactDto> updateContact(@PathVariable Long id,
      @RequestBody ContactDto contactDto) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contactService.update(id, contactDto));
  }

  @ApiOperation(value = "Remove an available contact -fetch by Id")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteContact(@PathVariable Long id) {
    contactService.delete(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body("Contact information removed successfully !");
  }
}
