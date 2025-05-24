package org.persona.moneyjar.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.AuthService;
import org.persona.moneyjar.service.Base64Service;
import org.persona.moneyjar.service.EncryptionService;
import org.persona.moneyjar.service.SessionService;
import org.persona.moneyjar.utils.JwtInformationUtil;
import org.springframework.stereotype.Service;

/**
 * @author Satya
 * @created 04/01/2025 - 01:54
 **/
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Base64Service base64Service;
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final SessionService sessionService;
    private final JWTServiceImpl jwtService;

    @Override
    public String authenticate(String token, HttpServletRequest httpRequest) {
        String[] parts = base64Service.decode(token).split(":");
        if (parts.length != 2) return null;
        var userOpt = userRepository.findByUsername(parts[0]);
        if (userOpt.isEmpty()) return null;
        String decryptedPassword = encryptionService.decrypt(userOpt.get().getPassword());
        if (!parts[1].equals(decryptedPassword)) {
            return null;
        }
        String jwtToken = jwtService.createToken(userOpt.get());
        sessionService.createSession(userOpt.get().getId(), jwtToken, httpRequest);
        return jwtToken;
    }

    @Override
    public void logout(String token) {
        sessionService.invalidateSession(JwtInformationUtil.getUserDetails().getId(), token);
    }
}
