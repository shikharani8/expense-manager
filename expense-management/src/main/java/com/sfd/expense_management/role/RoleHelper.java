package com.sfd.expense_management.role;

import com.sfd.expense_management.role.dtos.RoleRequestPayload;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleHelper {
    public void updateRoleValues(Role role, RoleRequestPayload roleRequestPayload){
        if(StringUtils.isNotEmpty(roleRequestPayload.getName())){
            role.setName(roleRequestPayload.getName());
        }
    }
}
