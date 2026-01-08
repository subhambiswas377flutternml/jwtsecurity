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
                .sessionManagement(sesssion-> sesssion.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session makes sure that we don't store any session
                .csrf(csrf-> csrf.disable()) // Cross site request forgery , we disable it as we don't need the login form or sping's default security as we will use Jwt
                .headers(headers-> headers.frameOptions(frame-> frame.sameOrigin())) // This is used as spring security disables <frame></frame> in html, but h2 console relies on it, so we need as long as we are using h2 memory
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/h2/console/**","/auth/***").permitAll() // This line denotes that any endpoint/url that has "/h2/console/anything" or "/auth/anything" in it, it will be permitted even if they don't have a jwt in their header, we need it to let the basic authentication url and h2 console url work, as they definitely wouldn't have any auth jwt
                        .anyRequest().authenticated()) // makes sure any other request apart from the ones above has to be authenticated, meaning they mandatorily have to carry a auth jwt in the Authorization header, or else they will be forbidden
                .addFilterBefore(authMiddleware, UsernamePasswordAuthenticationFilter.class); // we are adding the filter that will parse and check the jwt , Mind that addFilterBefore means this will work even before DispatcherServlet

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder is one of the best password encoder in the industry
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager(); // This Bean is necessary as we are using AuthenticationManager in AuthService for login
    }
}

