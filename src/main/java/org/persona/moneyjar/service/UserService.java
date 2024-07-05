package org.persona.moneyjar.service;

import org.persona.moneyjar.dto.UserDTO;
import org.persona.moneyjar.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Satya
 * @created 05/07/2024 - 09:42
 **/
public interface UserService {



    Optional<User> findByUsername(String username);
    String createUser(UserDTO user);
    Optional<User> findUserById(UUID id);
    boolean updateUser(UUID id, UserDTO user);
    boolean deleteUser(UUID id);
    List<User> getAllUser();
}
