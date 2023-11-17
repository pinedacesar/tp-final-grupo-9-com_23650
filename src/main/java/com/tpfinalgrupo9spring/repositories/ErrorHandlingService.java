package com.tpfinalgrupo9spring.repositories;

import org.springframework.dao.DuplicateKeyException;

public interface ErrorHandlingService {
    String getErrorMessage(DuplicateKeyException e);
}
