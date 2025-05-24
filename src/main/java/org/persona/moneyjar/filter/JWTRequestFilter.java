package org.persona.moneyjar.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.model.dto.DeviceInfo;
import org.persona.moneyjar.model.dto.UserSession;
import org.persona.moneyjar.model.entity.User;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.DeviceIdentifierService;
import org.persona.moneyjar.service.SessionService;
import org.persona.moneyjar.service.impl.JWTServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 00:17
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JWTServiceImpl jwtService;
    private final DeviceIdentifierService deviceIdentifierService;
    private final SessionService sessionService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> excludedPaths = Arrays.asList(
            "/auth/login",
            "/public/**",
            "/actuator/health"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String token = auth.substring(7);

        Long userId = jwtService.verifyToken(token);
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Authentication detail = new UsernamePasswordAuthenticationToken(user.get(), token, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(detail);

        DeviceInfo currentDevice = deviceIdentifierService.extractDeviceInfo(request);
        UserSession storedSession = sessionService.getSession(userId, token);
        if (storedSession == null || !deviceIdentifierService.isDeviceMatch(currentDevice, storedSession.getDeviceInfo())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        if ("/user".equals(path) && "POST".equalsIgnoreCase(method)) {
            return true;
        }
        return excludedPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}

