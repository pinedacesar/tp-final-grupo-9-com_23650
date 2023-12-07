package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.TpFinalApplication;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.AccountDTO;
import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.entities.enums.AccountType;
import com.tpfinalgrupo9spring.exceptions.AliasDuplicatedException;
import com.tpfinalgrupo9spring.exceptions.CbuDuplicatedException;
import com.tpfinalgrupo9spring.exceptions.SaveAccountException;
import com.tpfinalgrupo9spring.exceptions.UserNotFoundException;
import com.tpfinalgrupo9spring.mappers.UserMapper;
import com.tpfinalgrupo9spring.repositories.UserRepository;
import com.tpfinalgrupo9spring.utils.BCrypt;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;


    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService =accountService;
    }

    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserMapper::userToDto).collect(Collectors.toList());
    }

    public Object getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + " No encontrado"));
        return UserMapper.userToDto(user);
    }

    public UserDto createUser(UserDto user) throws UserNotFoundException, CbuDuplicatedException, AliasDuplicatedException, SaveAccountException {
        UserEntity entity = UserMapper.dtoToUser(user);
        entity.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        entity.setCreated_at(LocalDateTime.now());
        entity.setUpdated_at(LocalDateTime.now());
        UserEntity entitySaved = userRepository.save(entity);
        AccountDTO cuentaAutomatica =AccountDTO.builder()
                .ownerId(entity.getId())
                .tipo(AccountType.ARS_SAVINGS_BANK)
                .sucursal("111")
                .build();

        cuentaAutomatica.setTipo(AccountType.ARS_SAVINGS_BANK);
        cuentaAutomatica.setSucursal("100");
        cuentaAutomatica.setOwner(UserMapper.userToDto(entitySaved));
        accountService.createAccount(cuentaAutomatica);
        UserEntity newUser=userRepository.findById(entitySaved.getId()).get();
        user = UserMapper.userToDto(newUser);
        return user;
    }

    public Object updateUser(Long id, UserDto newUser) {
        UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + " No encontrado"));
        updateAttributes(userToUpdate, newUser);
        UserEntity userUpdated = userRepository.save(userToUpdate);

        return UserMapper.userToDto(userUpdated);
    }


    public String deleteUser(Long id) {
        UserEntity userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + " No encontrado"));
        userRepository.deleteById(id);
        return "El usuario " + userToDelete.getUsername() + " ha sido eliminado";
    }
    public Object updatePassword(Long id, String newPassword, String oldPassword) {
        UserEntity userToUpdate = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + " No encontrado"));

        if (BCrypt.checkpw(oldPassword, userToUpdate.getPassword())){
            userToUpdate.setPassword(BCrypt.hashpw(newPassword,BCrypt.gensalt()));
        }else{
            return "La contraseña actual " + oldPassword + " es incorrecta";
        }
        userRepository.save(userToUpdate);
        return "La contraseña del usuario " + userToUpdate.getUsername() + " ha sido actualizada";
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    private void updateAttributes(UserEntity userToUpdate, UserDto newUser) {

        if (newUser.getUsername() != null) {
            userToUpdate.setUsername(newUser.getUsername());
        }
        if (newUser.getFirstname() != null) {
            userToUpdate.setFirstname(newUser.getFirstname());
        }
        if (newUser.getLastname() != null) {
            userToUpdate.setLastname(newUser.getLastname());
        }
        if (newUser.getPassword() != null) {
            userToUpdate.setPassword(newUser.getPassword());
        }
        if (newUser.getEmail() != null) {
            userToUpdate.setEmail(newUser.getEmail());
        }
        if (newUser.getAddress() != null) {
            userToUpdate.setAddress(newUser.getAddress());
        }
        if (newUser.getDni() != null) {
            userToUpdate.setDni(newUser.getDni());
        }
        if (newUser.getBirthday_date() != null) {
            userToUpdate.setBirthday_date(newUser.getBirthday_date());
        }
        userToUpdate.setUpdated_at(LocalDateTime.now());
    }

}

