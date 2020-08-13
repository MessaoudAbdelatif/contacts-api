package com.abdelatif.contactsapi.validation;

import com.abdelatif.contactsapi.service.implementation.UserApiServiceImpl;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {


  private UserApiServiceImpl userApiService;


  public UniqueUsernameValidator(UserApiServiceImpl userApiService) {
    this.userApiService = userApiService;
  }

  String message;


  @Override
  public void initialize(UniqueUsername constraintAnnotation) {
    message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return userApiService.usernameAvailable(s);
  }
}
