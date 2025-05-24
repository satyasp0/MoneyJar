package org.persona.moneyjar.model.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Agus Setiawan Popalia
 * @created 24/05/2025 - 12:52
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    private Long userId;
    private String token;
    private DeviceInfo deviceInfo;
    private LocalDateTime loginTime;
    private LocalDateTime lastAccessTime;
    private boolean active;
}
