package org.persona.moneyjar.service;

import org.persona.moneyjar.model.entity.User;

/**
 * @author Satya
 * @created 04/01/2025 - 12:51
 **/
public interface JWTService {
    String createToken(User user);
    Long verifyToken(String token);
}
