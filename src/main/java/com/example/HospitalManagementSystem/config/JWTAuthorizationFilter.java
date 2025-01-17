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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); 

            String username = JWTUtil.getUsername(token);

            if (username != null && !JWTUtil.isTokenExpired(token)) {
                
                User user = (User) customUserDetailsService.loadUserByUsername(username);

                
                ArrayList<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities()); 

                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response); 
    }
}
