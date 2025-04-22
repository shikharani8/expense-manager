package com.sfd.expense_management.security;

import com.sfd.expense_management.user.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class JwtUtils {
    @Value("${security.secret.key}")
    private String signingKey;

    @Value("${security.secret.tokenExpirationInMins}")
    private int tokenExpirationInMins;

    public String getJwtTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        throw new InvalidCredentialsException("Bearer token can not be null!", HttpStatus.BAD_REQUEST.value());
    }

    public void validate(String jwtToken){
        try{
            Jws<Claims> jwt = jwtParse(jwtToken);
            if(!StringUtils.hasText(jwt.getBody().getSubject())
                    || Boolean.parseBoolean(Objects.toString(jwt.getBody().get("isRefreshToken")))){
                throw new InvalidCredentialsException("Provided Token is not valid", HttpStatus.UNAUTHORIZED.value());
            }
        }catch(Exception ex){
            throw new InvalidCredentialsException("Could not validate Json web Town", HttpStatus.UNAUTHORIZED.value());
        }
    }

    public Jws<Claims> jwtParse(String jwtToken){
        return Jwts.parser().setSigningKey(signingKey).build().parseSignedClaims(jwtToken);
    }

    public String getUsernameFromJwtToken(String jwtToken){
        try{
            Jws<Claims> claims = jwtParse(jwtToken);
            return claims.getBody().getSubject();
        }catch(Exception ex){
            throw new InvalidCredentialsException("Parse Token failed", HttpStatus.UNAUTHORIZED.value());
        }
    }

    public String generateJwtToken(UserDetails userDetails, boolean isRefreshToken){
        Instant now = Instant.now();
        log.info("Generating security tokens");

        return isRefreshToken ?
                Jwts.builder().claim("isRefreshToken", true)
                        .signWith(SignatureAlgorithm.HS512, signingKey)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(now.plus(tokenExpirationInMins, ChronoUnit.MINUTES)))
                        .compact()
                :
                Jwts.builder().claim("username", userDetails.getUsername())
                        .claim("roles", userDetails.getAuthorities())
                        .claim("isRefreshToken", false)
                        .signWith(SignatureAlgorithm.HS512, signingKey)
                        .setId(UUID.randomUUID().toString())
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(now.plus(tokenExpirationInMins, ChronoUnit.MINUTES)))
                        .compact();
    }
}
