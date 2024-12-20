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

        List<String> roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

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

    // TODO : simplify this method
    public Boolean validateAccessToken(String token, WebUserDetails userDetails){
        final String tokenUsername = extractUsername(token);
        final List roles = extractClaimsAndVerify(token).get("authorities", List.class);
        final String dbAccessToken = userDetails.getDBAccessToken();
        final String username = userDetails.getUsername();

        return username.equals(tokenUsername) && !isTokenExpired(token) && roles.contains("ROLE_USER") && dbAccessToken.equals(token);
    }

    public Boolean validateRefreshToken(String token, WebUserDetails userDetails){
        final String tokenUsername = extractUsername(token);
        final boolean refresh = extractRefreshToken(token).equals("true");
        final String username = userDetails.getUsername();
        final String dbRefreshToken = userDetails.getDBRefreshToken();

        return (tokenUsername.equals(username) && !isTokenExpired(token) && refresh && dbRefreshToken.equals(token));
    }
}
