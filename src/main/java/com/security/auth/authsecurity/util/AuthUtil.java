package com.security.auth.authsecurity.util;

import com.security.auth.authsecurity.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AuthUtil {
    @Value("${jwt.secretKey}")
    private String jwtSignatureKey;

    private SecretKey generateSecretKey(){
        return Keys.hmacShaKeyFor(jwtSignatureKey.getBytes());
    }

    public String generateJwtToken(UserEntity user){
        final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(generateSecretKey())
                .compact();
        return jwt;
    }
}
