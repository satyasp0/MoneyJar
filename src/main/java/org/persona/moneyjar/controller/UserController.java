package org.persona.moneyjar.controller;

import jakarta.validation.constraints.NotNull;
import org.persona.moneyjar.dto.BaseResponseDto;
import org.persona.moneyjar.dto.UserDTO;
import org.persona.moneyjar.entity.User;
import org.persona.moneyjar.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 13:17
 **/
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private static final String USER_NOT_FOUND = "default.user.message.not-found";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto> createUser(@Validated @RequestBody UserDTO user) {
        String id = userService.createUser(user);
        if(id!=null){
            return send201(id);
        }else {
            return send400("default.user.message.creation.failed");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getUserById(@PathVariable UUID id) {
        Optional<User> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            return send200(userOptional);
        }else{
            return send404(USER_NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateUser(@NotNull(message = "id can not be null") @PathVariable UUID id, @RequestBody UserDTO user) {
        if (userService.updateUser(id, user)){
            return send200("default.user.message.updated.success");
        }else {
            return send404(USER_NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteUser(@PathVariable UUID id) {
        if (userService.deleteUser(id)) {
            return send200("default.user.message.deleted.success");
        } else {
            return send404(USER_NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAllUsers() {
        return send200(userService.getAllUser());
    }
}

