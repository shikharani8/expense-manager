package com.sfd.expense_management.street;

import lombok.Data;

@Data
public class StreetException extends RuntimeException{
    private String message;
    private int errorCode;
    public StreetException(String message, int errorCode){
        super(message);
        this.message= message;
        this.errorCode= errorCode;
    }

    public StreetException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message= message;
        this.errorCode= errorCode;
    }
}
