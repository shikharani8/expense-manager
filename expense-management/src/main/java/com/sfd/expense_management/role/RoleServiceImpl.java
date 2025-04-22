package com.sfd.expense_management.role;

import com.sfd.expense_management.role.dtos.RoleRequestPayload;
import com.sfd.expense_management.user.UserException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final RoleHelper roleHelper;

    @Override
    public Role create(RoleRequestPayload roleRequestPayload) {
        Role existingRole = getRoleByName(roleRequestPayload.getName());
        if(Objects.isNull(existingRole)){
            Role role = new Role();
            role.setName(roleRequestPayload.getName());
            return roleRepository.save(role);
        }
        return existingRole;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(()->new RoleException("Role doesn't exist!", HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public Role update(Long roleId, RoleRequestPayload roleRequestPayload) {
        Role role = getRoleById(roleId);
        roleHelper.updateRoleValues(role, roleRequestPayload);
        return roleRepository.save(role);
    }

    @Override
    public String delete(Long roleId) {
        getRoleById(roleId);
        roleRepository.deleteById(roleId);
        return "Role deleted successfully!";
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
