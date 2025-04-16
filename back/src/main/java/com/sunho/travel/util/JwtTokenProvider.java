package com.sunho.travel.util;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

    private final long EXPIRATION_TIME = 1000 * 60; // 1분

    @Value("${jwt.secret.key}")
    private String JWT_SECRETE_KEY;

    public String generateToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder().setSubject(email) // 토큰 식별자
                .setIssuedAt(new Date()).setExpiration(expiry).signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRETE_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getIdFromToken(String token){
        return parseClaims(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            parseClaims(token);
            return true;   
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    }
}
