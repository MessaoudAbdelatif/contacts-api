package com.abdelatif.contactsapi.service.implementation;

import com.abdelatif.contactsapi.repository.UserApiDao;
import com.abdelatif.contactsapi.service.contract.UserApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserApiServiceImpl implements UserApiService {

  private final UserApiDao userApiDao;

  public boolean usernameNotAvailable(String username) {
    return userApiDao.findByUsername(username).isPresent();
  }
}
