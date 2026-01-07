package com.security.auth.authsecurity.service;

import com.security.auth.authsecurity.dto.AuthRequestDto;
import com.security.auth.authsecurity.dto.AuthResponseDto;
import com.security.auth.authsecurity.entity.UserEntity;
import com.security.auth.authsecurity.repository.UserRepository;
import com.security.auth.authsecurity.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public AuthResponseDto login(AuthRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        if(user!=null){
            String jwt = authUtil.generateJwtToken(user);

            return new AuthResponseDto(jwt, user.getUsername());
        }
        else{
            throw new IllegalArgumentException();
        }
    }


    public AuthResponseDto signup(AuthRequestDto authRequest) {
        UserEntity user = userRepository.getByUsername(authRequest.getUsername());
        if(user!=null) {
            throw new IllegalArgumentException();}
        else{
            UserEntity newUser = new UserEntity();
            newUser.setUsername(authRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));

            UserEntity savedUser = userRepository.save(newUser);

            String jwt = authUtil.generateJwtToken(savedUser);

            return new AuthResponseDto(jwt, newUser.getUsername());
        }
    }
}
