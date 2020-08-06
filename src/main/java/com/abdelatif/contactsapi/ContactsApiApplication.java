package com.abdelatif.contactsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ContactsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContactsApiApplication.class, args);
  }

}
