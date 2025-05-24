package org.persona.moneyjar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.persona.moneyjar.model.dto.UserSession;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:53
 **/
public interface SessionService {
    void createSession(Long userId, String token, HttpServletRequest request);
    UserSession getSession(Long userId, String token);
    boolean isSessionValid(Long userId, String token);
    void invalidateSession(Long userId, String token);
    void invalidateAllUserSessions(Long userId);
    void updateLastAccess(Long userId, String token);
    long getActiveSessionCount(Long userId);
}
