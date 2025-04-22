package com.sfd.expense_management.notifications;

import lombok.Data;

@Data
public class NotificationException extends RuntimeException{
    private  String message;
    private int errorCode;
    public NotificationException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public NotificationException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
