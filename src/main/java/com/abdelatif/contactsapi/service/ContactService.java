package com.abdelatif.contactsapi.service;

import com.abdelatif.contactsapi.dto.ContactDto;
import com.abdelatif.contactsapi.dto.Mapper.ContactMapper;
import com.abdelatif.contactsapi.exception.ContactApiException;
import com.abdelatif.contactsapi.model.Contact;
import com.abdelatif.contactsapi.model.UserApi;
import com.abdelatif.contactsapi.repository.ContactDao;
import com.abdelatif.contactsapi.repository.UserApiDao;
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
public class ContactService {

  private final ContactDao contactDao;
  private final UserApiDao userApiDao;
  private final ContactMapper contactMapper;
  private final AuthService authService;


  @Transactional
  public ContactDto save(ContactDto contactDto) {
    Contact save = contactDao.save(contactMapper.mapToContact(contactDto));
    UserApi currentUser = authService.getCurrentUser();
    currentUser.setContact(save);
    userApiDao.save(currentUser);
    contactDto.setId(save.getId());
    return contactDto;
  }

  @Transactional(readOnly = true)
  public List<ContactDto> getAll() {
    return contactDao.findAll()
        .stream()
        .map(contactMapper::mapToContactDto)
        .collect(Collectors.toList());
  }

  public ContactDto getContact(Long id) {
    Contact contact = contactDao.findById(id)
        .orElseThrow(() -> new ContactApiException("No contact found with this ID : " + id));
    return contactMapper.mapToContactDto(contact);
  }

  @Transactional
  public ContactDto update(Long id, ContactDto contactDto) {
    Optional<Contact> contact = contactDao.findById(id);
    if (contact.isPresent()) {
      contactDto.setId(id);
      contactDao.save(contactMapper.mapToContact(contactDto));
    } else {
      throw new ContactApiException("No contact found with this ID : " + id);
    }
    return contactDto;
  }

  @Transactional
  public void delete(Long id) {
    Optional<Contact> contact = contactDao.findById(id);
    if (contact.isPresent()) {
      contactDao.deleteById(id);
    } else {
      throw new ContactApiException("Can not delete ! no contact found with this ID : " + id);
    }
  }
}