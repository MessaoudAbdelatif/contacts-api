package com.abdelatif.contactsapi.dto.Mapper;

import com.abdelatif.contactsapi.dto.ContactDto;
import com.abdelatif.contactsapi.model.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

  Contact mapToContact(ContactDto contactDto);

  ContactDto mapToContactDto(Contact contact);

}
