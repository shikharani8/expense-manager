package com.sfd.expense_management.user;

import com.sfd.expense_management.user.dtos.ChangePasswordRequestPayload;
import com.sfd.expense_management.user.dtos.ForgetPasswordPayload;
import com.sfd.expense_management.user.dtos.ResetPasswordPayload;
import com.sfd.expense_management.user.dtos.UserCreatePayload;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    User create(UserCreatePayload userCreatePayload);
    User createSuperAdmin(User user);
    List<User> getUserList();
    User getUserById(Long id);
    User update(Long id, UserCreatePayload userCreatePayload);
    String delete(Long id);
    String updatePassword(ChangePasswordRequestPayload requestPayload);
    String updateProfile(String username, Map<String, String> newValues);
    String sendForgetPasswordOtp(ForgetPasswordPayload forgetPasswordPayload);
    String resetPassword(ResetPasswordPayload resetPasswordPayload);
    String updateNotificationSettings(String username, Map<String, Boolean> newValues);
}
