package com.treaps.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private JwtProperties jwtProperties;

    // Validate the JWT token
    public boolean validateToken(String token, String phoneNumber) {
        String extractedPhoneNumber = extractPhoneNumber(token);
        return (extractedPhoneNumber.equals(phoneNumber) && !isTokenExpired(token));
    }

    // Extract phone number from the JWT token
    public String extractPhoneNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract role from the JWT token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        String SECRET_KEY = jwtProperties.getSecretKey();
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Ensure a valid secret key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
