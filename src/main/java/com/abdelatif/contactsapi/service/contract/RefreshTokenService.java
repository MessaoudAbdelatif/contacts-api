package com.abdelatif.contactsapi.service.contract;

import com.abdelatif.contactsapi.model.RefreshToken;

public interface RefreshTokenService {

  RefreshToken generateRefreshToken();

  void validateRefreshToken(String token);

  void deleteRefreshToken(String token);
}

