package com.sfd.expense_management.house;

import lombok.Data;

@Data
public class HouseException extends RuntimeException{
    private String message;
    private int errorCode;

    public HouseException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public HouseException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
