package com.cjt.svc4.utils.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;
    @Value("${spring.jwt.header}")
    private String tokenRequestHeader; // "Authorization"
    @Value("${spring.jwt.refresh}")
    private String tokenRefreshRequestHeader; // "Authorization"

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    
    public String resolveToken(HttpServletRequest request) {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String token = wrappedRequest.getHeader(tokenRequestHeader);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String token = wrappedRequest.getHeader(tokenRefreshRequestHeader);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    public String getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userid", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String category, String userId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("category", category)
                .claim("userid", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
