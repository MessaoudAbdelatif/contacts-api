package com.abdelatif.contactsapi.exception;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class UsernameAlreadyUsedException implements ConstraintViolation {

  String message;

  public UsernameAlreadyUsedException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return null;
  }

  @Override
  public String getMessageTemplate() {
    return null;
  }

  @Override
  public Object getRootBean() {
    return null;
  }

  @Override
  public Class getRootBeanClass() {
    return null;
  }

  @Override
  public Object getLeafBean() {
    return null;
  }

  @Override
  public Object[] getExecutableParameters() {
    return new Object[0];
  }

  @Override
  public Object getExecutableReturnValue() {
    return null;
  }

  @Override
  public Path getPropertyPath() {
    return null;
  }

  @Override
  public Object getInvalidValue() {
    return null;
  }

  @Override
  public ConstraintDescriptor<?> getConstraintDescriptor() {
    return null;
  }

  @Override
  public Object unwrap(Class aClass) {
    return null;
  }
}
