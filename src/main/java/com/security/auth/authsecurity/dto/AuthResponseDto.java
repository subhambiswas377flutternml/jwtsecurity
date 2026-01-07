package com.security.auth.authsecurity.dto;

public class AuthResponseDto {
    private String accessToken;
    private String username;
    public AuthResponseDto(){}
    public AuthResponseDto(String accessToken, String username){
        this.accessToken=accessToken;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
