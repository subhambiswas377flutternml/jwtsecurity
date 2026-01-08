package com.security.auth.authsecurity.util;

import com.security.auth.authsecurity.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class AuthUtil {
    @Value("${jwt.secretKey}") // this annotation is used to Autowired data directly from application.properties file
    private String jwtSignatureKey;

    private SecretKey generateSecretKey(){
        return Keys.hmacShaKeyFor(jwtSignatureKey.getBytes());
    } // hMacShaKey is an algorithm

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

    public String extractUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generateSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
