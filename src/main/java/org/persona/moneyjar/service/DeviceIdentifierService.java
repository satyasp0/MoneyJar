package org.persona.moneyjar.service;

import jakarta.servlet.http.HttpServletRequest;
import org.persona.moneyjar.model.dto.DeviceInfo;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:45
 **/
public interface DeviceIdentifierService {
    DeviceInfo extractDeviceInfo(HttpServletRequest request);
    String getClientIpAddress(HttpServletRequest request);
    String generateFingerprint(DeviceInfo deviceInfo);
    boolean isDeviceMatch(DeviceInfo current, DeviceInfo stored);
}
