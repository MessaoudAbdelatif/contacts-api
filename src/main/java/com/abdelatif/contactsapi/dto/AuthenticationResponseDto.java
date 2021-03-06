package com.abdelatif.contactsapi.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {

  private String authenticationToken;
  private String username;
  private Instant expiresAt;
  private String refreshToken;

}
