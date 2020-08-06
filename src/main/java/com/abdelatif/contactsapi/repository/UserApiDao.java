package com.abdelatif.contactsapi.repository;

import com.abdelatif.contactsapi.model.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApiDao extends JpaRepository<UserApi, Long> {

}
