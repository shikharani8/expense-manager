package com.sfd.expense_management.expenseCategory;

import lombok.Data;

@Data
public class ExpenseCategoryException extends RuntimeException{
    private  String message;
    private int errorCode;
    public ExpenseCategoryException(String message, int errorCode){
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public ExpenseCategoryException(String message, int errorCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.errorCode = errorCode;
    }
}
