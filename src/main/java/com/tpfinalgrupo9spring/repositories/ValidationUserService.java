package com.tpfinalgrupo9spring.repositories;

import com.tpfinalgrupo9spring.entities.dtos.UserDto;

public interface ValidationUserService {
    void validateUniqueFields(UserDto user);
}
