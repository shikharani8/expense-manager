package com.sfd.expense_management.role;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleException extends RuntimeException{
    private String message;
    private int errorCode;
    public RoleException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public RoleException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
