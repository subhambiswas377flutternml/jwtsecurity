package com.security.auth.authsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private AuthMiddleware authMiddleware;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
        httpSecurity
                .sessionManagement(sesssion-> sesssion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf-> csrf.disable())
                .headers(headers-> headers.frameOptions(frame-> frame.sameOrigin()))
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/h2/console/**","/auth/***").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(authMiddleware, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }
}

