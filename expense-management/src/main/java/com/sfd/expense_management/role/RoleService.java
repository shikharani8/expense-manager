package com.sfd.expense_management.role;

import com.sfd.expense_management.role.dtos.RoleRequestPayload;

import java.util.List;

public interface RoleService {
    Role create(RoleRequestPayload roleRequestPayload);
    List<Role> getRoles();
    Role getRoleById(Long roleId);
    Role update(Long roleId, RoleRequestPayload roleRequestPayload);
    String delete(Long roleId);
    Role getRoleByName(String name);

}
