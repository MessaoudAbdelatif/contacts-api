package com.abdelatif.contactsapi;

import com.abdelatif.contactsapi.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class ContactsApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContactsApiApplication.class, args);
  }

}
