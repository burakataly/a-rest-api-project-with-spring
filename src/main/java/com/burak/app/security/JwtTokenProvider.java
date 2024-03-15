package com.burak.questApp.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.key}")
    private String SECRET;
    @Value("${expires.in}")
    private Long EXPIRES_IN;

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        Date expireDate = new Date(System.currentTimeMillis() + (EXPIRES_IN * 1000L));
         return Jwts.builder().
                setClaims(claims).setSubject(username).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(expireDate).
                 signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date getExpirationFromToken(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        try {
            String username = getUsernameFromToken(token);
            Date expiration = getExpirationFromToken(token);
            return username.equals(userDetails.getUsername()) && expiration.after(new Date());
        }catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
