package com.example.firstproject.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
