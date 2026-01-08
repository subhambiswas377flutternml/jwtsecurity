package com.security.auth.authsecurity.config;

import com.security.auth.authsecurity.entity.UserEntity;
import com.security.auth.authsecurity.repository.UserRepository;
import com.security.auth.authsecurity.util.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// This is a Security filter (OncePerRequestFilter) that will do the jwt validation, parsing and user fetching before DispatcherServlet
@Component
public class AuthMiddleware extends OncePerRequestFilter {
    @Autowired
    private AuthUtil authUtil; // used for parsing the jwt
    @Autowired
    private UserRepository userRepository; // used for checking if user found in jwt exists in database or not

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // fetching the value of Authorization header from request
        if(authHeader==null||!authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response); // This lets the request proceed and do springboot do the necessary handling, this is same as android's chain.proceed(request.build())
        }else{
            String token = authHeader.split(" ")[1];
            String username = authUtil.extractUsernameFromToken(token);

            // SecurityContextHolder.getContext().getAuthentication() should be null as we have to authenticate the request using UsernamePasswordAuthenticationToken
            if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserEntity user = userRepository.getByUsername(username);
                if(user!=null){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()); // user.getAuthorities() is a must, or else it will proceed the request as authenticated but not authorized
                    SecurityContextHolder.getContext().setAuthentication(authToken); // we are authorizing the request

                    filterChain.doFilter(request, response);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
