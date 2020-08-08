package com.abdelatif.contactsapi.controller;

import com.abdelatif.contactsapi.dto.AuthenticationResponseDto;
import com.abdelatif.contactsapi.dto.LoginRequestDto;
import com.abdelatif.contactsapi.dto.RegisterRequestDto;
import com.abdelatif.contactsapi.service.AuthService;
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

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequest) {
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
}
