package org.persona.moneyjar.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Satya
 * @created 04/01/2025 - 01:53
 **/
public interface AuthService {
    String authenticate(String token, HttpServletRequest httpRequest);
    void logout(String token);
}
