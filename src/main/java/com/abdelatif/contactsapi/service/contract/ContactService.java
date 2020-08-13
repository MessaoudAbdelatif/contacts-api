package com.abdelatif.contactsapi.service.contract;

import com.abdelatif.contactsapi.dto.ContactDto;
import java.util.List;
/**
 * Contact Service a simple Contact CRUD interface.
 * */
public interface ContactService {

  ContactDto save(ContactDto contactDto);

  List<ContactDto> getAll();

  ContactDto getContact(Long id);

  ContactDto update(Long id, ContactDto contactDto);

  void delete(Long id);
}
