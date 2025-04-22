package com.sfd.expense_management.user.dtos;

import com.sfd.expense_management.role.Role;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserCreatePayload {
private String username;
private String firstname;
private String lastname;
private String email;
private String password;
private Boolean isActive;
private Set<Role> roles = new HashSet<>();
private boolean emailNotificationEnabled;
private boolean smsNotificationEnabled;
private boolean pushNotificationEnabled;
private boolean whatsappNotificationEnabled;
}
