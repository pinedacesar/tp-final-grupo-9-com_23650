package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.mappers.UserMapper;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

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
    public Object updateUser(Long id, UserDto newUser) {
            UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario No encontrado con ID: " + id));
           updateAttributes(userToUpdate, newUser);
           UserEntity userUpdated = userRepository.save(userToUpdate);

           return UserMapper.userToDto(userUpdated);
    }


    public List<UserEntity> getUsers() {return userRepository.findAll();}


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByDni(String dni){
        return userRepository.existsByDni(dni);
    }
    private void updateAttributes(UserEntity userToUpdate, UserDto newUser) {

        if (newUser.getUsername() != null){
            userToUpdate.setUsername(newUser.getUsername());
        }
        if (newUser.getFirstname() != null){
            userToUpdate.setFirstname(newUser.getFirstname());
        }
        if (newUser.getLastname() != null){
            userToUpdate.setLastname(newUser.getLastname());
        }
        if (newUser.getPassword() != null){
            userToUpdate.setPassword(newUser.getPassword());
        }
        if (newUser.getEmail() != null){
            userToUpdate.setEmail(newUser.getEmail());
        }
        if (newUser.getAddress() != null){
            userToUpdate.setAddress(newUser.getAddress());
        }
        if (newUser.getBirthday_date() != null){
            userToUpdate.setBirthday_date(newUser.getBirthday_date());
        }
        userToUpdate.setUpdated_at(LocalDateTime.now());
    }

}

