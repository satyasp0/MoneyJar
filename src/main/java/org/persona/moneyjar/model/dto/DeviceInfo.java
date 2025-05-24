package org.persona.moneyjar.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:46
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo {
    private String userAgent;
    private String acceptLanguage;
    private String acceptEncoding;
    private String xForwardedFor;
    private String xRealIp;
    private String clientIp;
    private String deviceFingerprint;

    private String screenResolution;
    private String timezone;
    private String platform;
    private String deviceType;
    private String browserName;
    private String browserVersion;
    private String operatingSystem;
}
