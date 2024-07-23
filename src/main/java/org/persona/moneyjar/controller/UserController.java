package org.persona.moneyjar.controller;

import jakarta.validation.constraints.NotNull;
import org.persona.moneyjar.contants.MessageConstants;
import org.persona.moneyjar.dto.BaseResponseDto;
import org.persona.moneyjar.dto.UserDTO;
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
            return send400(MessageConstants.CREATE_FAILED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto> getUserById(@PathVariable UUID id) {
        Optional<UserDTO> userOptional = userService.findUserById(id);
        if(userOptional.isPresent()){
            return send200(userOptional);
        }else{
            return send404(MessageConstants.USER_NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto> updateUser(@NotNull(message = "id can not be null") @PathVariable UUID id, @RequestBody UserDTO user) {
        if (userService.updateUser(id, user)){
            return send200(MessageConstants.USER_UPDATED);
        }else {
            return send404(MessageConstants.USER_NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto> deleteUser(@PathVariable UUID id) {
        if (userService.deleteUser(id)) {
            return send200(MessageConstants.USER_DELETED);
        } else {
            return send404(MessageConstants.USER_NOT_FOUND);
        }
    }
}

