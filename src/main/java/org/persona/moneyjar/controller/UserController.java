package org.persona.moneyjar.controller;

import jakarta.validation.constraints.NotNull;
import org.persona.moneyjar.model.dto.BaseResponseDto;
import org.persona.moneyjar.model.dto.UserDTO;
import org.persona.moneyjar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Satya
 * @created 05/07/2024 - 13:17
 **/
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createUser(@Validated @RequestBody UserDTO user) {
        Long id = userService.createUser(user);
        return send201(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getUserById(@PathVariable Long id) {
        UserDTO userOptional = userService.findUserById(id);
        return send200(userOptional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateUser(@NotNull(message = "id can not be null") @PathVariable Long id, @RequestBody UserDTO user) {
        userService.updateUser(id, user);
        return send200("user updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return send200("user deleted successfully");
    }
}

