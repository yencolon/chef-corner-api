package org.chefcorner.chefcorner.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-expiration}")
    private long accessExpirationTimeInMs;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationTimeInMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateAccessToken(WebUserDetails userDetails) {

        String roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .add("authorities", roles)
                .build();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer("chefcorner")
                .expiration(new Date(System.currentTimeMillis() + accessExpirationTimeInMs))
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(WebUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("refresh", "true")
                .issuer("chefcorner")
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTimeInMs))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractClaimsAndVerify(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaimsAndVerify(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRefreshToken(String token){
        return extractClaimsAndVerify(token).get("refresh").toString();
    }

    public Boolean validateAccessToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        final List roles = extractClaimsAndVerify(token).get("roles", List.class);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    public Boolean validateRefreshToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        final boolean refresh = extractRefreshToken(token).equals("true");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && token.contains("refresh"));
    }



}
