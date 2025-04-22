package com.sfd.expense_management.auth.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
