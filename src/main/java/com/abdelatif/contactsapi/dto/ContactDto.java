package com.abdelatif.contactsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

  private Long id;
  private String firstname;
  private String lastname;
  private String fullname;
  private String address;
  private String email;
  private String mobileNumber;

}
