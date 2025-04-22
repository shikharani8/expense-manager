package com.sfd.expense_management.user;

import com.sfd.expense_management.security.InvalidCredentialsException;
import com.sfd.expense_management.user.dtos.UserCreatePayload;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserHelper {
    public User updateUser(User user, UserCreatePayload userCreatePayload){
        if(StringUtils.isNotEmpty(userCreatePayload.getUsername())){
            user.setUsername(userCreatePayload.getUsername());
        }
        if(StringUtils.isNotEmpty(userCreatePayload.getFirstname())){
            user.setFirstname(userCreatePayload.getFirstname());
        }
        if(StringUtils.isNotEmpty(userCreatePayload.getLastname())){
            user.setLastname(userCreatePayload.getLastname());
        }
        if(StringUtils.isNotEmpty(userCreatePayload.getEmail())){
            user.setEmail(userCreatePayload.getEmail());
        }
        if(Objects.nonNull(userCreatePayload.getIsActive())){
            user.setIsActive(userCreatePayload.getIsActive());
        }
        if(StringUtils.isNotEmpty(userCreatePayload.getPassword())){
            user.setPassword(userCreatePayload.getPassword());
        }
        if(!userCreatePayload.getRoles().isEmpty()){
            user.setRoles(userCreatePayload.getRoles());
        }
      return user;
    }

    public static User getLoggedInUser(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (User) authentication.getPrincipal();
        }catch(Exception ex){
            throw new UserException("User not logged in", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
