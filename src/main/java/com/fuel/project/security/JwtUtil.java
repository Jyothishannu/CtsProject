package com.fuel.project.security;
 
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
 
import java.util.Date;
 
@Component
public class JwtUtil {
 
    private final String SECRET = "mysecretkey";
 
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
 
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
 
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isExpired(token);
    }
 
    private boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
 
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}