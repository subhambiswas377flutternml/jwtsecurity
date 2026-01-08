package com.security.auth.authsecurity.repository;

import com.security.auth.authsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getByUsername(String username); //getBy<Attribute name in Upper Camel Case> is a method in spring jpa
}
