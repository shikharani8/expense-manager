package com.sfd.expense_management.initializer;

import com.sfd.expense_management.role.Role;
import com.sfd.expense_management.role.RoleService;
import com.sfd.expense_management.role.dtos.RoleRequestPayload;
import com.sfd.expense_management.society.Society;
import com.sfd.expense_management.society.SocietyService;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserService;
import com.sfd.expense_management.expenseCategory.ExpenseCategory;
import com.sfd.expense_management.expenseCategory.ExpenseCategoryService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class InitialData {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ExpenseCategoryService expenseCategoryService;
    @Autowired
    private SocietyService societyService;
    @Value("${superAdminRole}")
    private String roleName;
    @Value("${superAdminUserName}")
    private String superAdminUserName;

    @PostConstruct
    private void createInitialData(){
        createRole();
        createUser();
        createExpenseCategory();
        createSociety();
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
        if(existingUser.isEmpty()){
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

    public void createExpenseCategory(){
        List<ExpenseCategory> expenseCategoryList = List.of(
                new ExpenseCategory("Sanitation", true),
                new ExpenseCategory("Guard_Salary", true),
                new ExpenseCategory("Garbage_Collection", true),
                new ExpenseCategory("Electric_Equipment", true),
                new ExpenseCategory("Miscellaneous", true));

        List<ExpenseCategory> existingList = expenseCategoryService.getAll();
        List<String> expenseNameList = existingList.stream().map(ExpenseCategory::getName).toList();
        expenseCategoryList.stream()
                .filter(expense-> !expenseNameList.contains(expense.getName()))
                .forEach(expense->  expenseCategoryService.create(expense));
    }

    public void createSociety(){
        String societyName = "Vrindavan Garden";
        List<Society> societyList= societyService.getAll();
        Optional<Society> existingSociety = societyList.stream().filter(society -> societyName.equals(society.getName())).findFirst();
        if(!existingSociety.isPresent()){
            Society society = new Society();
            society.setName(societyName);
            Role role = roleService.getRoleByName(roleName);
            Optional<User> superAdmin = userService.getUserList().stream().filter(user->superAdminUserName.equals(user.getUsername())).findFirst();
            superAdmin.ifPresent(society::setSuperAdmin);
            societyService.create(society);
        }else{
            log.warn("Society already exist!");
        }

    }
}
