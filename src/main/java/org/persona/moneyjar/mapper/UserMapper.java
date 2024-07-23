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

    public UserDTO entityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPicture(user.getPicture());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
