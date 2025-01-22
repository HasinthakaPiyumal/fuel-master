package com.uokse.fuelmaster.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class Jwtutil {

    @Value("${jwt.secret}")
    private String secret;

    private long expiration;

    public String generateToken(String phone) {
        return Jwts.builder()
            .setSubject(phone)
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

public boolean validateToken(String token, String phone){
        return phone.equals(extractPhone(token)) && !isTokenExpired(token);
        
}
    public String extractPhone(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}