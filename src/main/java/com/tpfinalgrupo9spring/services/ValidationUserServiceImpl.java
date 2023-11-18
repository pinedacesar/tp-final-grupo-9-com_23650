package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.exceptions.ErrorCode;
import com.tpfinalgrupo9spring.repositories.ValidationUserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class ValidationUserServiceImpl implements ValidationUserService {
    private final UserService userService;

    public ValidationUserServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validateUniqueFields(UserDto user) {
        validateUniqueUsername(user.getUsername());
        validateUniqueEmail(user.getEmail());
        validateUniqueDni(user.getDni());
    }

    private void validateUniqueUsername(String username) {
        if (userService.existsByUsername(username)) {
            throw new DuplicateKeyException(ErrorCode.DEFAULT_MESSAGE);
        }
    }

    private void validateUniqueEmail(String email) {
        if (userService.existsByEmail(email)) {
            throw new DuplicateKeyException(ErrorCode.DEFAULT_MESSAGE);
        }
    }

    private void validateUniqueDni(String dni) {
        if (userService.existsByDni(dni)) {
            throw new DuplicateKeyException(ErrorCode.DEFAULT_MESSAGE);
        }
    }
}
