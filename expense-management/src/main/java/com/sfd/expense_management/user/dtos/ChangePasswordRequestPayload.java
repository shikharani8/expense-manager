package com.sfd.expense_management.user.dtos;

import lombok.Data;

@Data
public class ChangePasswordRequestPayload {
    private String oldPassword;
    private String newPassword;
    private String rePassword;
    private String username;
}
