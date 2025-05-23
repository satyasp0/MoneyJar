package org.persona.moneyjar.controller.auth;

import lombok.AllArgsConstructor;
import org.persona.moneyjar.controller.BaseController;
import org.persona.moneyjar.model.dto.BaseResponseDto;
import org.persona.moneyjar.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Satya
 * @created 04/01/2025 - 01:29
 **/

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDto> login(@RequestHeader(name = "Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return send400("Missing or invalid Authorization header");
        }
        String jwtToken = authService.authenticate(authHeader.substring("Basic ".length()).trim());
        if (Objects.isNull(jwtToken)) return send400("Unauthorized");
        return send200(jwtToken);
    }

}
