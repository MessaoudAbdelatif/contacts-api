package com.abdelatif.contactsapi.service.implementation;

import com.abdelatif.contactsapi.dto.AuthenticationResponseDto;
import com.abdelatif.contactsapi.dto.LoginRequestDto;
import com.abdelatif.contactsapi.dto.NotificationEmailDto;
import com.abdelatif.contactsapi.dto.RefreshTokenRequest;
import com.abdelatif.contactsapi.dto.RegisterRequestDto;
import com.abdelatif.contactsapi.exception.ContactApiException;
import com.abdelatif.contactsapi.model.UserApi;
import com.abdelatif.contactsapi.model.VerificationToken;
import com.abdelatif.contactsapi.repository.UserApiDao;
import com.abdelatif.contactsapi.repository.VerificationTokenDao;
import com.abdelatif.contactsapi.security.JwtProvider;
import com.abdelatif.contactsapi.service.contract.UserApiService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final RefreshTokenServiceImpl refreshTokenServiceImpl;
  private final UserApiService userApiService;


  @Transactional
  public void signup(RegisterRequestDto registerRequestDto) {
    if (userApiService.usernameNotAvailable(registerRequestDto.getUsername())) {
      throw new ContactApiException("This username is already used please choose another one !");
    } else {
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
              "Thank you for signing up to Contact-API ," +
                  "please click on the link below to activate your account : " +
                  "http://localhost:8080/api/auth/accountVerification/" + token));
    }
  }
  public String generateVerificationToken(UserApi userApi) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setUserApi(userApi);
    verificationToken.setToken(token);

    verificationTokenDao.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    Optional<VerificationToken> verificationToken = verificationTokenDao.findByToken(token);
    verificationToken.orElseThrow(() -> new ContactApiException("Invalid Token !!"));
    fetchUserAndEnable(verificationToken.get());
  }


  public void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUserApi().getUsername();

    UserApi foundUserApi = userApiDao.findByUsername(username)
        .orElseThrow(() -> new ContactApiException("User " + username + " not found !"));

    foundUserApi.setEnable(true);
    userApiDao.save(foundUserApi);
  }

  public AuthenticationResponseDto login(LoginRequestDto loginRequestDto) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
            loginRequestDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String token = jwtProvider.generateToken(authenticate);
    return AuthenticationResponseDto.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenServiceImpl.generateRefreshToken().getToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(loginRequestDto.getUsername())
        .build();
  }

  @Transactional(readOnly = true)
  public UserApi getCurrentUser() {
    User principal = (User) SecurityContextHolder.
        getContext().getAuthentication().getPrincipal();
    return userApiDao.findByUsername(principal.getUsername())
        .orElseThrow(
            () -> new ContactApiException("Username not found - " + principal.getUsername()));
  }

  public AuthenticationResponseDto refreshToken(RefreshTokenRequest refreshTokenRequest) {
    refreshTokenServiceImpl
        .validateRefreshToken(refreshTokenRequest.getRefreshToken()); // can throw an error
    String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
    return AuthenticationResponseDto.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(refreshTokenRequest.getUsername())
        .build();
  }
}
