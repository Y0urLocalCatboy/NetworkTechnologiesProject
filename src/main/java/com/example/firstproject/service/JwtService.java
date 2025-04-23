package com.example.firstproject.service;

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

    private final String SECRET = "secretkdsafsadfasfsaffsafsafsafsafsafvxasfakfjsahgjaey";
    private final long TOKEN_VALIDITY = 1000*60*60;

    public String createToken(String email){
        long now = System.currentTimeMillis();
        var token = Jwts.builder().subject(email)
                .claim("roles","Customer")
                .issuedAt(new Date(now))
                .expiration(new Date(now+(TOKEN_VALIDITY)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()));
        return token.compact();
    }

    public boolean verifyToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String getUsername(String token) {
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
