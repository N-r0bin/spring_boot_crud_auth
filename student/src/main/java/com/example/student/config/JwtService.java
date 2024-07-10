package com.example.student.config;

import com.example.student.student.Student;
import com.example.student.token.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "CWQCLn7guSVkVN3ZwXgmVTsyXYasnMS8Kr+n5xC8sW7ipJfJ7iEi85lR6Cy1Q581";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Token generateToken(Student student) {
        return generateToken(new HashMap<>(), student);
    }


    public Token generateToken(Map<String, Object> extraClaims, Student student) {
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(student.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(expiryDate.toInstant(ZoneOffset.UTC)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return Token.builder().expiryDate(expiryDate).token(token).student(student).build();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}