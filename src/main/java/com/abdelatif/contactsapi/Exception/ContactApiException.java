package com.abdelatif.contactsapi.Exception;

public class ContactApiException extends RuntimeException {

  public ContactApiException(String exMessage, Exception exception) {
    super(exMessage, exception);
  }

  public ContactApiException(String exMessage) {
    super(exMessage);
  }

}
