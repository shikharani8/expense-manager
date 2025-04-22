package com.sfd.expense_management.exception;

import com.sfd.expense_management.notifications.NotificationException;
import com.sfd.expense_management.role.RoleException;
import com.sfd.expense_management.security.InvalidCredentialsException;
import com.sfd.expense_management.user.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<GenericExceptionResponse> handleException(RoleException roleException){

        return ResponseEntity.status(roleException.getErrorCode())
                .body(GenericExceptionResponse.builder().withMessage(roleException.getMessage()).build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<GenericExceptionResponse> handleException(UserException userException){
        return ResponseEntity.status(userException.getErrorCode())
                .body(GenericExceptionResponse.builder().withMessage(userException.getMessage()).build());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GenericExceptionResponse> handleException(InvalidCredentialsException invalidCredentialsException){
        return ResponseEntity.status(invalidCredentialsException.getErrorCode())
                .body(GenericExceptionResponse.builder().withMessage(invalidCredentialsException.getMessage()).build());
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<GenericExceptionResponse> handleException(NotificationException notificationException){
        return ResponseEntity.status(notificationException.getErrorCode())
                .body(GenericExceptionResponse.builder().withMessage(notificationException.getMessage()).build());
    }
}
