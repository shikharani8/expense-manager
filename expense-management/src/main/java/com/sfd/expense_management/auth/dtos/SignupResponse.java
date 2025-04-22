package com.sfd.expense_management.auth.dtos;

import com.sfd.expense_management.role.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SignupResponse {
    private String username;
    private String email;
    private String message;
}
