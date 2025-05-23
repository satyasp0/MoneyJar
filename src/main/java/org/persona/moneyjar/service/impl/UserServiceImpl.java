package org.persona.moneyjar.service.impl;

import org.persona.moneyjar.exception.MoneyJarException;
import org.persona.moneyjar.model.dto.UserDTO;
import org.persona.moneyjar.model.entity.User;
import org.persona.moneyjar.mapper.UserMapper;
import org.persona.moneyjar.repository.UserRepository;
import org.persona.moneyjar.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.persona.moneyjar.utils.MapperUtils.updateField;

/**
 * @author Satya
 * @created 05/07/2024 - 09:42
 **/

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Long createUser(UserDTO user) {
        try {
            User userEntity = userMapper.dtoToEntity(user);
            return userRepository.save(userEntity).getId();
        } catch (DataIntegrityViolationException e) {
            throw MoneyJarException.userCreationError();
        }
    }

    @Override
    public UserDTO findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw MoneyJarException.userNotFoundError();
        return userMapper.entityToDto(user.get());
    }

    @Override
    public void updateUser(Long id, UserDTO user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw MoneyJarException.userNotFoundError();
        updateField(user.getUsername(), userOptional.get()::setUsername);
        updateField(user.getPassword(), userOptional.get()::setPassword);
        updateField(user.getName(), userOptional.get()::setName);
        updateField(user.getPicture(), userOptional.get()::setPicture);
        updateField(user.getEmail(), userOptional.get()::setEmail);
        userRepository.save(userOptional.get());
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw MoneyJarException.userNotFoundError();
        User existingUser = userOptional.get();
        existingUser.setEnabled(false);
        userRepository.save(existingUser);
    }
}
