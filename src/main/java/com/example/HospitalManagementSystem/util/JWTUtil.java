package com.example.HospitalManagementSystem.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET_KEY = "qv4L+N9llGCpTponjz7YF9PV8nLdWDjb1Vp0vLpQ8hYU733BtDnRRR7kGc/ted/1OL+ZFsjglSY7WCXZgfFnNlHITa/zfcxSlfGb+Hs/3lA=";  
    private static final long EXPIRATION_TIME = 6300000; 

    public static String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    public static Claims extractClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static String getUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public static boolean validateToken(String token, String username) {
        return (username.equals(getUsername(token)) && !isTokenExpired(token));
    }
}
