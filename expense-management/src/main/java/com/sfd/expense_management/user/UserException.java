package com.sfd.expense_management.user;

import lombok.Data;

@Data
public class UserException extends RuntimeException{
    private String message;
    private int errorCode;
    public UserException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public UserException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
