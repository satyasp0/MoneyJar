package org.persona.moneyjar.mapper;

import org.persona.moneyjar.dto.UserDTO;
import org.persona.moneyjar.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Satya
 * @created 05/07/2024 - 14:13
 **/

@Component
public class UserMapper {

    public User dtoToEntity(UserDTO user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setPicture(user.getPicture());
        userEntity.setUsername(user.getUsername());
        return userEntity;
    }
}
