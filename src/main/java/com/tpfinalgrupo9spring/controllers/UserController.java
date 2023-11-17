package com.tpfinalgrupo9spring.controllers;

import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.repositories.ErrorHandlingService;
import com.tpfinalgrupo9spring.repositories.ValidationUserService;
import com.tpfinalgrupo9spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final ValidationUserService validationUserService;
    private final ErrorHandlingService errorHandlingService;

    public UserController(UserService service, ValidationUserService validationUserService, ErrorHandlingService errorHandlingService) {
        this.service = service;
        this.validationUserService = validationUserService;
        this.errorHandlingService = errorHandlingService;
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto user) {
        try {

            validationUserService.validateUniqueFields(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
        } catch (DuplicateKeyException e) {
            String errorMessage = errorHandlingService.getErrorMessage(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", errorMessage));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
