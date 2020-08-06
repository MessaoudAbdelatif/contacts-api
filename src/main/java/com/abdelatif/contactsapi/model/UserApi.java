package com.abdelatif.contactsapi.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserApi implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  //@UniqueElements(message = "This username is already used !")
  private String username;

  @NotBlank(message = "Password is required, minimum size 5 characters !")
  @Size(min = 5, message = "Password is required, minimum size 5 characters !")
  private String password;

  private String confirmedPassword;

  @NotNull
  @Email(message = "Valid Email is required !")
  private String email;

  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdDate;

  private boolean enable;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Contact contact;
}
