package com.abdelatif.contactsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

  private String email;
 // @UniqueUsername(message = "Sorry this username is already used !")
  private String username;
  private String password;
}