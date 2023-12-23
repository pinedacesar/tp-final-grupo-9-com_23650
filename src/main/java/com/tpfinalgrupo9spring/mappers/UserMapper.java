package com.tpfinalgrupo9spring.mappers;

import com.tpfinalgrupo9spring.entities.Accounts;
import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.AccountDTO;
import com.tpfinalgrupo9spring.entities.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserEntity dtoToUser(UserDto dto){
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setDni(dto.getDni());
        user.setBirthday_date(dto.getBirthday_date());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setAccounts(dto.getAccounts());
        user.setAddress(dto.getAddress());
        user.setCreated_at(dto.getCreated_at());
        user.setUpdated_at(dto.getUpdated_at());
        return user;
    }

    public static UserDto userToDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setDni(user.getDni());
        dto.setBirthday_date(user.getBirthday_date());

        dto.setAccounts(user.getAccounts());

//        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        return dto;
    }

    public static UserDto userToDtoShort(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setDni(user.getDni());
        dto.setBirthday_date(user.getBirthday_date());

//        dto.setAccounts(user.getAccounts());
//
//        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        return dto;
    }
}
