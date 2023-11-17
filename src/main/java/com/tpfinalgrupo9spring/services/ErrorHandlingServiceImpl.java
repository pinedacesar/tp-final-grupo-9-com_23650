package com.tpfinalgrupo9spring.services;

import com.tpfinalgrupo9spring.exceptions.ErrorCode;
import com.tpfinalgrupo9spring.repositories.ErrorHandlingService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class ErrorHandlingServiceImpl implements ErrorHandlingService {
    @Override
    public String getErrorMessage(DuplicateKeyException e) {
        return switch (e.getMessage()) {
            case ErrorCode.DUPLICATE_USERNAME -> ErrorCode.MESSAGE_DUPLICATE_USERNAME;
            case ErrorCode.DUPLICATE_EMAIL -> ErrorCode.MESSAGE_DUPLICATE_EMAIL;
            case ErrorCode.DUPLICATE_DNI -> ErrorCode.MESSAGE_DUPLICATE_DNI;
            default -> ErrorCode.DEFAULT_MESSAGE;
        };
    }
}
