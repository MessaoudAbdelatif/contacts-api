package com.abdelatif.contactsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

  private String email;
  private String username;
  private String password;
  private String confirmedPassword;

}
