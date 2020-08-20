package com.abdelatif.contactsapi.repository;

import com.abdelatif.contactsapi.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  void deleteByToken(String token);
}
