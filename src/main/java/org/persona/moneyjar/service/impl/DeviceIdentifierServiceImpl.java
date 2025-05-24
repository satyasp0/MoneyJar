package org.persona.moneyjar.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.model.dto.DeviceInfo;
import org.persona.moneyjar.service.DeviceIdentifierService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:48
 **/
@Service
@Slf4j
public class DeviceIdentifierServiceImpl implements DeviceIdentifierService {
    private static final Pattern BROWSER_PATTERN = Pattern.compile("(Chrome|Firefox|Safari|Edge|Opera)/(\\d+\\.\\d+)");
    private static final Pattern OS_PATTERN = Pattern.compile("(Windows NT|Mac OS X|Linux|Android|iPhone OS) ([\\d._]+)");

    @Override
    public DeviceInfo extractDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String acceptLanguage = request.getHeader("Accept-Language");
        String acceptEncoding = request.getHeader("Accept-Encoding");
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        String xRealIp = request.getHeader("X-Real-IP");
        String clientIp = getClientIpAddress(request);

        DeviceInfo deviceInfo = DeviceInfo.builder()
                .userAgent(userAgent)
                .acceptLanguage(acceptLanguage)
                .acceptEncoding(acceptEncoding)
                .xForwardedFor(xForwardedFor)
                .xRealIp(xRealIp)
                .clientIp(clientIp)
                .build();

        // Parse additional info from User-Agent
        if (userAgent != null) {
            parseUserAgent(userAgent, deviceInfo);
        }

        // Generate device fingerprint
        deviceInfo.setDeviceFingerprint(generateFingerprint(deviceInfo));

        return deviceInfo;
    }

    @Override
    public String generateFingerprint(DeviceInfo deviceInfo) {
        try {
            String combined = (deviceInfo.getUserAgent() != null ? deviceInfo.getUserAgent() : "") +
                              "|" +
                              (deviceInfo.getAcceptLanguage() != null ? deviceInfo.getAcceptLanguage() : "") +
                              "|" +
                              (deviceInfo.getAcceptEncoding() != null ? deviceInfo.getAcceptEncoding() : "") +
                              "|" +
                              (deviceInfo.getScreenResolution() != null ? deviceInfo.getScreenResolution() : "") +
                              "|" +
                              (deviceInfo.getTimezone() != null ? deviceInfo.getTimezone() : "");

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            log.error("Failed to generate device fingerprint: {}", e.getMessage());
            throw MoneyJarException.generalError();
        }
    }

    @Override
    public boolean isDeviceMatch(DeviceInfo current, DeviceInfo stored) {
        if (current == null || stored == null) {
            return false;
        }
        String currentFingerprint = current.getDeviceFingerprint();
        String storedFingerprint = stored.getDeviceFingerprint();
        if (currentFingerprint != null && storedFingerprint != null) {
            return currentFingerprint.equals(storedFingerprint);
        }
        return compareUserAgents(current.getUserAgent(), stored.getUserAgent()) &&
               compareLanguages(current.getAcceptLanguage(), stored.getAcceptLanguage());
    }

    @Override
    public String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        String xRealIpHeader = request.getHeader("X-Real-IP");
        if (xRealIpHeader != null && !xRealIpHeader.isEmpty()) {
            return xRealIpHeader;
        }

        return request.getRemoteAddr();
    }

    private void parseUserAgent(String userAgent, DeviceInfo deviceInfo) {
        Matcher browserMatcher = BROWSER_PATTERN.matcher(userAgent);
        if (browserMatcher.find()) {
            deviceInfo.setBrowserName(browserMatcher.group(1));
            deviceInfo.setBrowserVersion(browserMatcher.group(2));
        }

        Matcher osMatcher = OS_PATTERN.matcher(userAgent);
        if (osMatcher.find()) {
            deviceInfo.setOperatingSystem(osMatcher.group(1) + " " + osMatcher.group(2));
        }

        if (userAgent.toLowerCase().contains("mobile")) {
            deviceInfo.setDeviceType("mobile");
        } else if (userAgent.toLowerCase().contains("tablet")) {
            deviceInfo.setDeviceType("tablet");
        } else {
            deviceInfo.setDeviceType("desktop");
        }
    }

    private boolean compareUserAgents(String current, String stored) {
        if (current == null || stored == null) {
            return Objects.equals(current, stored);
        }

        String currentMajor = extractMajorVersion(current);
        String storedMajor = extractMajorVersion(stored);

        return currentMajor.equals(storedMajor);
    }

    private boolean compareLanguages(String current, String stored) {
        return current.equals(stored);
    }

    private String extractMajorVersion(String userAgent) {
        Matcher matcher = BROWSER_PATTERN.matcher(userAgent);
        if (matcher.find()) {
            String version = matcher.group(2);
            int dotIndex = version.indexOf('.');
            if (dotIndex > 0) {
                return matcher.group(1) + "/" + version.substring(0, dotIndex);
            }
            return matcher.group(1) + "/" + version;
        }
        return userAgent;
    }
}
