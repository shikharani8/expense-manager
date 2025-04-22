package com.sfd.expense_management.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class GenericExceptionResponse {
    private String message;
}
