package com.abdelatif.contactsapi.service;

import com.abdelatif.contactsapi.dto.RegisterRequestDto;
import com.abdelatif.contactsapi.dto.NotificationEmailDto;
import com.abdelatif.contactsapi.model.UserApi;
import com.abdelatif.contactsapi.model.VerificationToken;
import com.abdelatif.contactsapi.repository.UserApiDao;
import com.abdelatif.contactsapi.repository.VerificationTokenDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserApiDao userApiDao;
  private final VerificationTokenDao verificationTokenDao;
  private final MailService mailService;

  @Transactional
  public void signup(RegisterRequestDto registerRequestDto) {
    UserApi userApi = new UserApi();
    userApi.setUsername(registerRequestDto.getUsername());
    userApi.setEmail(registerRequestDto.getEmail());
    userApi.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
    userApi.setCreatedDate(Timestamp.from(Instant.now()));
    userApi.setEnable(false);
    userApiDao.save(userApi);
    String token = generateVerificationToken(userApi);

    mailService.sendEmail(
        new NotificationEmailDto("Please activate your Account",
            userApi.getEmail(),
            "Thank you for signing up to Contact-API ,"+
                "please click on the link below to activate your account : "+
            "http://localhost:8080/api/auth/accountVerification/" + token));
  }

  public String generateVerificationToken(UserApi userApi) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUserApi(userApi);
    verificationToken.setToken(token);

    verificationTokenDao.save(verificationToken);
    return token;


  }
}
