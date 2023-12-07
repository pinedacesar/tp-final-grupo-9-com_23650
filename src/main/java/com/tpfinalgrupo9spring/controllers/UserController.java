package com.tpfinalgrupo9spring.controllers;

import com.tpfinalgrupo9spring.entities.UserEntity;
import com.tpfinalgrupo9spring.entities.dtos.UserDto;
import com.tpfinalgrupo9spring.exceptions.AliasDuplicatedException;
import com.tpfinalgrupo9spring.exceptions.CbuDuplicatedException;
import com.tpfinalgrupo9spring.exceptions.SaveAccountException;
import com.tpfinalgrupo9spring.exceptions.UserNotFoundException;
import com.tpfinalgrupo9spring.repositories.ErrorHandlingService;
import com.tpfinalgrupo9spring.repositories.ValidationUserService;
import com.tpfinalgrupo9spring.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUsers());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto user) {
        try {

            validationUserService.validateUniqueFields(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
        } catch (DuplicateKeyException e) {
            String errorMessage = errorHandlingService.getErrorMessage(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", errorMessage));
        } catch (Exception | UserNotFoundException | CbuDuplicatedException | AliasDuplicatedException |
                 SaveAccountException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userUpdated) {
        try {
            validationUserService.validateUniqueFields(userUpdated);
            return ResponseEntity.status(HttpStatus.OK).body(service.updateUser(id, userUpdated));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (DuplicateKeyException e) {
            String errorMessage = errorHandlingService.getErrorMessage(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", errorMessage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.deleteUser(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PatchMapping("/updatePassword/user/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        try {
            String newPassword = passwordMap.get("nueva_contrasenia");
            String oldPassword = passwordMap.get("contrasenia_actual");
            if (newPassword == null || oldPassword == null) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(service.updatePassword(id, newPassword, oldPassword));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        } catch (DuplicateKeyException e) {
            String errorMessage = errorHandlingService.getErrorMessage(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("error", errorMessage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
