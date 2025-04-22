package com.sfd.expense_management.user.dtos;

import lombok.Data;

@Data
public class ForgetPasswordPayload {
    private String username;
    private String email;
}
