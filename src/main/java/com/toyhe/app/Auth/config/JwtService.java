package com.toyhe.app.Auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtService {
    private static final String SECRET_KEY = "D76BA0C99405CDB23F0B18613970A7352A429E051344AF053523915D977B5DDDB5F5523800DCEEDF2317765ACEC9666EAE06975EE45CA9CD09DA200D209B24ADAF115AE937FD267E1C0AA7B76BDBC0EDE0A1FAB0AB4AC6BE3ABE2B4D8CED7A83205ABF3E8AAE0EEFFAF2F93DF9908C605654F31C686F4D05A5C2239F1770606C";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //generate token
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String authorityString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .claim("authorities" , authorityString)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // one hour for testing
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(final  String token){
       Jwts.parser().setSigningKey(getSignInkey()).build().parseClaimsJws(token);
    }

    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public List<String> getAuthorities(String token) {
        log.info("The token received is  :  {} " ,  token);
        Claims claims = extractAllClaims(token);
        String authorities = claims.get("authorities", String.class);
        return Arrays.asList(authorities.split(","));
    }


}
