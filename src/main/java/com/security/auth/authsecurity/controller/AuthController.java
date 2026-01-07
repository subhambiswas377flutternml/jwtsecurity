package com.security.auth.authsecurity.controller;

import com.security.auth.authsecurity.dto.AuthRequestDto;
import com.security.auth.authsecurity.dto.AuthResponseDto;
import com.security.auth.authsecurity.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private AuthService authService;
    AuthController(AuthService authService){
        this.authService=authService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request){
        try{
            AuthResponseDto authResponse = authService.login(request);
            return ResponseEntity.ok(authResponse);
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody AuthRequestDto request){
        try{
            AuthResponseDto authResponseDto = authService.signup(request);
            return ResponseEntity.ok(authResponseDto);
        }catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
