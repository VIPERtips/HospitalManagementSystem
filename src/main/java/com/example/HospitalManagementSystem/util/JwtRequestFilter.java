package com.example.HospitalManagementSystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
/*
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private static String SECRET_KEY;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String token = extractTokenFromHeader(request);

		if (token != null && JwtUtil.validateToken(token, getUsernameFromToken(token))) {
			Claims claims = JwtUtil.extractClaims(token);
			String username = claims.getSubject();
			// Set the user context based on the claims in the token
			// For example, you can authenticate the user using a custom Authentication
			// object
		}

		chain.doFilter(request, response);
	}

	private String extractTokenFromHeader(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	private String getUsernameFromToken(String token) {
		return JwtUtil.extractUsername(token);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
		// TODO Auto-generated method stub

	}
}*/
