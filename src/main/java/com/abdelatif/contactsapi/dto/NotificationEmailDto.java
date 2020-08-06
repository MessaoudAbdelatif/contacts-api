package com.abdelatif.contactsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmailDto {

  private String subject;
  private String recipient;
  private String body;

}
