package com.example.HospitalManagementSystem.config;

import com.example.HospitalManagementSystem.service.CustomUserDetailsService;
import com.example.HospitalManagementSystem.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JWTAuthorizationFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("Invalid token format or missing token");
            filterChain.doFilter(request, response);
            return; // Return if token is missing or in an invalid format
        }

        token = token.substring(7); // Remove "Bearer " prefix

        try {
            // Validate and extract claims from the token
            if (token != null && !token.isEmpty()) {
                String username = JWTUtil.getUsername(token);
                if (username != null && JWTUtil.validateToken(token, username)) {
                    // Proceed with authentication if valid
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Invalid token or expired");
                }
            }
        } catch (Exception e) {
            // Handle invalid token scenario
            System.out.println("Error while processing token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
