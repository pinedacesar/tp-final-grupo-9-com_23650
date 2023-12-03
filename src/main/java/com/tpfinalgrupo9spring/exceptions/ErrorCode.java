package com.tpfinalgrupo9spring.exceptions;

public class ErrorCode {
    public static final String DUPLICATE_USERNAME = "DUPLICATE_USERNAME";
    public static final String DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
    public static final String DUPLICATE_DNI = "DUPLICATE_DNI";

    public static final String MESSAGE_DUPLICATE_USERNAME = "El nombre de usuario ya existe";
    public static final String MESSAGE_DUPLICATE_EMAIL = "El correo electrónico ya existe";
    public static final String MESSAGE_DUPLICATE_DNI = "El número de documento ya existe";
    public static final String DEFAULT_MESSAGE = "Credenciales inválidas";
}
