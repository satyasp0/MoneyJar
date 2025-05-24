package org.persona.moneyjar.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.model.dto.DeviceInfo;
import org.persona.moneyjar.model.dto.UserSession;
import org.persona.moneyjar.service.DeviceIdentifierService;
import org.persona.moneyjar.service.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:54
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final DeviceIdentifierService deviceIdentifierService;

    @Value("${session.redis.timeout:86400}")
    private long sessionTimeout;

    private static final String SESSION_KEY_PREFIX = "user_session:";
    private static final String USER_SESSIONS_PREFIX = "user_sessions:";

    @Override
    public void createSession(Long userId, String token, HttpServletRequest request) {
        try {
            DeviceInfo deviceInfo = deviceIdentifierService.extractDeviceInfo(request);

            UserSession session = UserSession.builder()
                    .userId(userId)
                    .token(token)
                    .deviceInfo(deviceInfo)
                    .loginTime(LocalDateTime.now())
                    .lastAccessTime(LocalDateTime.now())
                    .active(true)
                    .build();

            String sessionKey = generateSessionKey(userId, token);
            String userSessionsKey = USER_SESSIONS_PREFIX + userId;

            redisTemplate.opsForValue().set(sessionKey, session);
            redisTemplate.expire(sessionKey, sessionTimeout, TimeUnit.SECONDS);
            redisTemplate.opsForSet().add(userSessionsKey, sessionKey);
            redisTemplate.expire(userSessionsKey, sessionTimeout, TimeUnit.SECONDS);

            log.info("Session created for user {} with device fingerprint", userId);
        } catch (Exception e) {
            log.error("Failed to create session for user {}: {}", userId, e.getMessage());
            throw MoneyJarException.generalError();
        }
    }

    @Override
    public UserSession getSession(Long userId, String token) {
        try {
            String sessionKey = generateSessionKey(userId, token);
            UserSession session = (UserSession) redisTemplate.opsForValue().get(sessionKey);

            if (session != null && session.isActive()) {
                // Update last access time
                session.setLastAccessTime(LocalDateTime.now());
                redisTemplate.opsForValue().set(sessionKey, session);
                redisTemplate.expire(sessionKey, sessionTimeout, TimeUnit.SECONDS);
                return session;
            }

            return null;
        } catch (Exception e) {
            log.error("Failed to get session for user {}: {}", userId, e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isSessionValid(Long userId, String token) {
        UserSession session = getSession(userId, token);
        return session != null && session.isActive();
    }

    @Override
    public void invalidateSession(Long userId, String token) {
        try {
            String sessionKey = generateSessionKey(userId, token);
            String userSessionsKey = USER_SESSIONS_PREFIX + userId;

            // Remove from individual session storage
            redisTemplate.delete(sessionKey);

            // Remove from user's session set
            redisTemplate.opsForSet().remove(userSessionsKey, sessionKey);

            log.info("Session invalidated for user {}", userId);
        } catch (Exception e) {
            log.error("Failed to invalidate session for user {}: {}", userId, e.getMessage());
        }
    }

    @Override
    public void invalidateAllUserSessions(Long userId) {
        try {
            String userSessionsKey = USER_SESSIONS_PREFIX + userId;
            Set<Object> sessionKeys = redisTemplate.opsForSet().members(userSessionsKey);

            if (sessionKeys != null && !sessionKeys.isEmpty()) {
                redisTemplate.delete(sessionKeys.stream().map(Object::toString).toList());
                redisTemplate.delete(userSessionsKey);
                log.info("All sessions invalidated for user {}", userId);
            }
        } catch (Exception e) {
            log.error("Failed to invalidate all sessions for user {}: {}", userId, e.getMessage());
        }
    }

    @Override
    public void updateLastAccess(Long userId, String token) {
        try {
            String sessionKey = generateSessionKey(userId, token);
            UserSession session = (UserSession) redisTemplate.opsForValue().get(sessionKey);

            if (session != null) {
                session.setLastAccessTime(LocalDateTime.now());
                redisTemplate.opsForValue().set(sessionKey, session, sessionTimeout, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("Failed to update last access for user {}: {}", userId, e.getMessage());
        }
    }

    @Override
    public long getActiveSessionCount(Long userId) {
        try {
            String userSessionsKey = USER_SESSIONS_PREFIX + userId;
            Long count = redisTemplate.opsForSet().size(userSessionsKey);
            return count != null ? count : 0;
        } catch (Exception e) {
            log.error("Failed to get active session count for user {}: {}", userId, e.getMessage());
            return 0;
        }
    }

    private String generateSessionKey(Long userId, String token) {
        String tokenPrefix = token.length() >= 10 ? token.substring(0, 10) : token;
        return SESSION_KEY_PREFIX + userId + ":" + tokenPrefix;
    }
}
