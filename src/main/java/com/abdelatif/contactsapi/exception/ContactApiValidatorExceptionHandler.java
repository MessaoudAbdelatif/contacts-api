package com.abdelatif.contactsapi.exception;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContactApiValidatorExceptionHandler {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<Object> handleContactApiValidatorException(ConstraintViolationException e) {
    List<String> collect = e.getConstraintViolations().stream()
        .map(ConstraintViolation::getMessageTemplate).collect(
            Collectors.toList());

    ContactApiValidatorException contactApiValidatorException = new ContactApiValidatorException(
        collect.toString()
        , HttpStatus.UNPROCESSABLE_ENTITY,
        ZonedDateTime.now());
    return new ResponseEntity<>(contactApiValidatorException, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
