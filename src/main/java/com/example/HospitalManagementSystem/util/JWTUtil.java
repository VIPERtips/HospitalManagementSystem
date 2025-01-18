package com.example.HospitalManagementSystem.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET_KEY = "qv4L+N9llGCpTponjz7YF9PV8nLdWDjb1Vp0vLpQ8hYU733BtDnRRR7kGc/ted/1OL+ZFsjglSY7WCXZgfFnNlHITa/zfcxSlfGb+Hs/3lA=";  
    private static final long EXPIRATION_TIME = 86400000L; // 24 hours

    // Generate JWT token
    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    // Extract claims from JWT token
    public static Claims extractClaims(String token) {
        if (token == null || token.isEmpty() || !token.contains(".")) {
            throw new IllegalArgumentException("Invalid JWT token: " + token);  // Handle malformed token
        }
        
        System.out.println("Token received: " + token);  // Debugging line to log the token
        
        try {
            return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Token parsing failed: " + e.getMessage());
        }
    }

    // Check if the token is expired
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Get the username from the JWT token
    public static String getUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Validate the token
    public static boolean validateToken(String token, String username) {
        return (username.equals(getUsername(token)) && !isTokenExpired(token));
    }
}
