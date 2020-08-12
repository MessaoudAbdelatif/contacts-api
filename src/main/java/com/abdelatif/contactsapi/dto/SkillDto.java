package com.abdelatif.contactsapi.dto;

import com.abdelatif.contactsapi.model.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDto {

  private Long id;
  private String name;
  private Level level;
}
