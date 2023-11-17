package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.mappers.UserMapper;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto user){
        UserEntity entity = UserMapper.dtoToUser(user);
        UserEntity entitySaved = userRepository.save(entity);
        user = UserMapper.userToDto(entitySaved);
        return user;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByDni(String dni){
        return userRepository.existsByDni(dni);
    }

}

