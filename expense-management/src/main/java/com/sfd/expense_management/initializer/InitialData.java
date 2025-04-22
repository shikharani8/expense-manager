package com.sfd.expense_management.initializer;

import com.sfd.expense_management.role.Role;
import com.sfd.expense_management.role.RoleService;
import com.sfd.expense_management.role.dtos.RoleRequestPayload;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserException;
import com.sfd.expense_management.user.UserService;
import com.sfd.expense_management.user.dtos.UserCreatePayload;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class InitialData {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${superAdminRole}")
    private String roleName;
    @Value("${superAdminUserName}")
    private String superAdminUserName;

    @PostConstruct
    private void createInitialData(){
        createRole();
        createUser();
    }

    private void createRole(){
        try{
            RoleRequestPayload roleRequestPayload = new RoleRequestPayload();
            roleRequestPayload.setName(roleName);
            roleService.create(roleRequestPayload);
        }catch(Exception ex){
            System.out.println("Failed to initialize the role-" +ex);
        }
    }

    public void createUser(){
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getRoleByName(roleName));
        List<User> userList = userService.getUserList();
        Optional<User> existingUser = userList.stream().filter(user->superAdminUserName.equals(user.getUsername())).findFirst();
        if(!existingUser.isPresent()){
            User user = new User();
            user.setUsername("superadmin");
            user.setPassword(passwordEncoder.encode("superadmin"));
            user.setFirstname("superadmin");
            user.setLastname("superadmin");
            user.setEmail("superadmin@email.com");
            user.setIsActive(true);
            user.setRoles(roleSet);
            user.setEmailNotificationEnabled(true);
            userService.createSuperAdmin(user);
        }else{
            log.warn("User already exist!");
        }
    }
}
