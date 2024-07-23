package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.dto.UserDTO;
import org.persona.moneyjar.entity.User;
import org.persona.moneyjar.mapper.UserMapper;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.persona.moneyjar.utils.MapperUtils.updateField;

/**
 * @author Satya
 * @created 05/07/2024 - 09:42
 **/

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public String createUser(UserDTO user) {
        try {
            User userEntity = userMapper.dtoToEntity(user);
            return userRepository.save(userEntity).getId().toString();
        } catch (DataIntegrityViolationException e) {
            return null;

        }
    }

    @Override
    public Optional<UserDTO> findUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::entityToDto);
    }

    @Override
    public boolean updateUser(UUID id, UserDTO user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            updateField(user.getUsername(), existingUser::setUsername);
            updateField(user.getPassword(), existingUser::setPassword);
            updateField(user.getName(), existingUser::setName);
            updateField(user.getPicture(), existingUser::setPicture);
            updateField(user.getEmail(), existingUser::setEmail);

            userRepository.save(existingUser);
            return true;
        } else return false;
    }

    @Override
    public boolean deleteUser(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setEnabled(false);
            userRepository.save(existingUser);
            return true;
        } else return false;
    }
}
