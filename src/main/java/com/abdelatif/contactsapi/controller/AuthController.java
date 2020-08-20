package com.abdelatif.contactsapi.controller;

import com.abdelatif.contactsapi.dto.AuthenticationResponseDto;
import com.abdelatif.contactsapi.dto.LoginRequestDto;
import com.abdelatif.contactsapi.dto.RefreshTokenRequest;
import com.abdelatif.contactsapi.dto.RegisterRequestDto;
import com.abdelatif.contactsapi.service.implementation.AuthService;
import com.abdelatif.contactsapi.service.implementation.RefreshTokenServiceImpl;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenServiceImpl refreshTokenServiceImpl;


  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody @Valid RegisterRequestDto registerRequest) {
    authService.signup(registerRequest);
    return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
  }

  @GetMapping("/accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable String token) {
    authService.verifyAccount(token);
    return new ResponseEntity<>("Account activated successfully !", HttpStatus.OK);
  }

  @PostMapping("/login")
  public AuthenticationResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    return authService.login(loginRequestDto);
  }

  @PostMapping("refresh/token")
  public AuthenticationResponseDto refreshTokens(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return authService.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    refreshTokenServiceImpl.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    return ResponseEntity.status(HttpStatus.OK).body("Refresh Token deleted successfully !");
  }
}
