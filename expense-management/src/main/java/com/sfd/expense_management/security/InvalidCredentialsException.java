package com.sfd.expense_management.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {
    private final String message;
    
    @Getter
    private int errorCode;
    public InvalidCredentialsException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public InvalidCredentialsException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
