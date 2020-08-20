package com.abdelatif.contactsapi.service.implementation;

import com.abdelatif.contactsapi.exception.ContactApiException;
import com.abdelatif.contactsapi.model.RefreshToken;
import com.abdelatif.contactsapi.repository.RefreshTokenDao;
import com.abdelatif.contactsapi.service.contract.RefreshTokenService;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenDao refreshTokenDao;

  public RefreshToken generateRefreshToken() {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setCreatedDate(Instant.now());
    return refreshTokenDao.save(refreshToken);
  }

  public void validateRefreshToken(String token) {
    refreshTokenDao.findByToken(token)
        .orElseThrow(() -> new ContactApiException("Invalid refresh Token"));
  }

  public void deleteRefreshToken(String token) {
    refreshTokenDao.deleteByToken(token);
  }
}