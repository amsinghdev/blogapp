package com.amit.blogapp.security;

import com.amit.blogapp.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    // generate jwt token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationInMs);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
        return token;
    }

    //get username from token
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //validate token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        }catch (SecurityException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Expired JWT token");
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }
    }
}