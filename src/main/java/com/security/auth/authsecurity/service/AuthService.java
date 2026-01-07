package com.security.auth.authsecurity.service;

import com.security.auth.authsecurity.dto.AuthRequestDto;
import com.security.auth.authsecurity.dto.AuthResponseDto;
import com.security.auth.authsecurity.repository.UserRepository;
import com.security.auth.authsecurity.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthRequestDto authRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
    }
}
