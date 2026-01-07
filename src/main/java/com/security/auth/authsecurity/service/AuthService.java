package com.security.auth.authsecurity.service;

import com.security.auth.authsecurity.dto.AuthRequestDto;
import com.security.auth.authsecurity.dto.AuthResponseDto;
import com.security.auth.authsecurity.repository.UserRepository;
import com.security.auth.authsecurity.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUtil authUtil;

    public AuthResponseDto login(AuthRequestDto authRequest){

    }
}
