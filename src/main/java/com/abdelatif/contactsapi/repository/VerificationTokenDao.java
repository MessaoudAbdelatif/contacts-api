package com.abdelatif.contactsapi.repository;

import com.abdelatif.contactsapi.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long> {

}
