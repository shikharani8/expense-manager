package com.sfd.expense_management.auth.dtos;

import com.sfd.expense_management.role.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthResponse {
    private String username;
    private List<String> roles;
    private String token;
}
