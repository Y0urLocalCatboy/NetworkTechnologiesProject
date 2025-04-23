package com.example.firstproject.service;

import com.example.firstproject.structure.entity.CustomerEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

//use login dto as input
@Service
public class JwtService {

    private final String SECRET = "mySuperSecretKeyWithAtLeast32Characters123!";
    private final long TOKEN_VALIDITY = 1000*60*60;

    public String createToken(CustomerEntity customer){
        long now = System.currentTimeMillis();
        var token = Jwts.builder()
                .subject(customer.getEmail())
                .claim("id", customer.getId())
                .issuedAt(new Date(now))
                .expiration(new Date(now+(TOKEN_VALIDITY)))
                .signWith(generateKey())
                .compact();
        return token;
    }

    public boolean verifyToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}
