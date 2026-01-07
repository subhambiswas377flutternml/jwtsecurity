package com.security.auth.authsecurity.repository;

import com.security.auth.authsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getByUsername(String username);
}
