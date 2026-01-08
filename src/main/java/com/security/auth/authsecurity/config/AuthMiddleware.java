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

@Component
public class AuthMiddleware extends OncePerRequestFilter {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
        }else{
            String token = authHeader.split("Bearer")[1];
            String username = authUtil.extractUsernameFromToken(token);

            if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserEntity user = userRepository.getByUsername(username);
                if(user!=null){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    filterChain.doFilter(request, response);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }
}
