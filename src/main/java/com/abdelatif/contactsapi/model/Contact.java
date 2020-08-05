package com.abdelatif.contactsapi.model;


import com.abdelatif.contactsapi.validation.FieldMatch;
import com.abdelatif.contactsapi.validation.ValidPhoneNum;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmedPassword", errorMessage = "Confirm password is different from the password !!")})

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long contactId;
  @NotNull
  @NotBlank(message = "Firstname is required !")
  private String firstname;
  @NotNull
  @NotBlank(message = "Lastname is required !")
  private String lastname;
  @NotBlank(message = "Fullname is required !")
  private String fullname;
  @NotNull
  @NotBlank(message = "Address is required !")
  private String address;
  @NotNull
  @Email(message = "Valid Email is required !")
  private String email;
  @NotNull
  @ValidPhoneNum(message = "Valid mobile phone number required !")
  private String mobileNumber;
  @NotBlank(message = "Password is required, minimum size 5 characters !")
  @Size(min = 5, max = 50)
  private String password;
  @NotBlank
  private String confirmedPassword;

  private Boolean enable;

  @ManyToMany
  @JoinTable(
      name = "contact_skills",
      joinColumns = {@JoinColumn(name = "contact_id")},
      inverseJoinColumns = {@JoinColumn(name = "skill_id")}
  )
  private List<Skill> skills;
}
