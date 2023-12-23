package com.tpfinalgrupo9spring.exceptions;

public class TransferNotFoundException extends RuntimeException{
    public TransferNotFoundException(String message) {
        super(message);
    }
}
