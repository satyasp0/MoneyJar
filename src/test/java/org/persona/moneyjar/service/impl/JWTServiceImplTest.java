package org.persona.moneyjar.service.impl;

import org.junit.jupiter.api.Test;
import org.persona.moneyjar.model.entity.User;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Satya
 * @created 04/01/2025 - 12:56
 **/

class JWTServiceImplTest {
    String testResult = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoicGVyc29uYSIsImlhdCI6MTczNTk3MDYwM30.aV-2Qz-xs0sOo1K_thHMKPkuWq0f_tjpCDlyRo6hf1A";

    @Test
    void createToken() {
        JWTServiceImpl jwtService = new JWTServiceImpl();
        String token = jwtService.createToken(new User());
        assertNotNull(token);
    }

    @Test
    void verifyToken() {
        JWTServiceImpl jwtService = new JWTServiceImpl();
        Long result = jwtService.verifyToken(testResult);
        assertEquals(1L, result);
    }
}