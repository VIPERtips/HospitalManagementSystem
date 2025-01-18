package com.example.HospitalManagementSystem.config;

import com.example.HospitalManagementSystem.util.BufferedRequestWrapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.MediaType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.HospitalManagementSystem.model.User;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        
        HttpServletRequest wrappedRequest = new BufferedRequestWrapper(request);

        
        if (wrappedRequest.getMethod().equalsIgnoreCase("POST") && wrappedRequest.getRequestURI().equals("/api/login")) {
            if (wrappedRequest.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
               
                User credentials = new ObjectMapper().readValue(wrappedRequest.getReader(), User.class);

                String username = credentials.getUsername();
                String password = credentials.getPassword();

                if (username != null && password != null) {
                    
                    UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(username, password);

                    
                    authenticationManager.authenticate(authenticationToken);

                   
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(wrappedRequest, response); 
    }
}
