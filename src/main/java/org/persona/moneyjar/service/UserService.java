package org.persona.moneyjar.service;

import org.persona.moneyjar.dto.UserDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 09:42
 **/
public interface UserService {

    String createUser(UserDTO user);
    Optional<UserDTO> findUserById(UUID id);
    boolean updateUser(UUID id, UserDTO user);
    boolean deleteUser(UUID id);
}
