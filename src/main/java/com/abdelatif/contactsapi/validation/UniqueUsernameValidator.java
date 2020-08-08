package com.abdelatif.contactsapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  @Override
  public void initialize(UniqueUsername constraintAnnotation) {
    String message = constraintAnnotation.message();

  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return false;
  }
}
