package com.sfd.expense_management.society;

import lombok.Data;

@Data
public class SocietyException extends RuntimeException{
    private String message;
    private int errorCode;

    public SocietyException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public SocietyException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
