package com.sfd.expense_management.user.dtos;

import lombok.Data;

@Data
public class ResetPasswordPayload {
    private String username;
    private String password;
    private String otp;
}
