package com.abdelatif.contactsapi.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PhoneNumValidator implements ConstraintValidator<ValidPhoneNum, String> {

  @Override
  public void initialize(ValidPhoneNum constraintAnnotation) {

  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
    try {
      PhoneNumber sPhone = numberUtil
          .parse(s, "CH");  // For Swiss phone number (waiting for functional business details...)
      return numberUtil.isPossibleNumber(sPhone);
    } catch (NumberParseException e) {
      log.error("NumberParseException was thrown: " + e.toString());
    }
    return false;
  }
}
