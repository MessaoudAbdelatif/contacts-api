package com.abdelatif.contactsapi.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

  private String username;
  @NotBlank(message = "the refresh token can not be empty !")
  private String refreshToken;

}
