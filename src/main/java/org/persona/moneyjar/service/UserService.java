package org.persona.moneyjar.service;

import org.persona.moneyjar.model.dto.UserDTO;

/**
 * @author Satya
 * @created 05/07/2024 - 09:42
 **/
public interface UserService {

    Long createUser(UserDTO user);
    UserDTO findUserById(Long id);
    void updateUser(Long id, UserDTO user);
    void deleteUser(Long id);
}
