package org.persona.moneyjar.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.model.entity.User;
import org.persona.moneyjar.service.JWTService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Satya
 * @created 04/01/2025 - 12:52
 **/
@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

    private static final String SECRET_KEY = "s9z8X7y6W5v4U3t2R1q0PoNlMkJoIhGfEdCbA";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    @Override
    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", user.getUsername());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claims(claims)
                .issuedAt(new Date())
                .signWith(KEY)
                .compact();
    }

    @Override
    public Long verifyToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(KEY)
                    .build().parseSignedClaims(token);

            return claimsJws.getPayload().get("id", Long.class);
        } catch (Exception e) {
            log.info("Invalid token: " + e.getMessage());
            return null;
        }
    }
}
