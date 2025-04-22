package com.sfd.expense_management.user;

import com.sfd.expense_management.auth.otp.OTP;
import com.sfd.expense_management.auth.otp.OTPHelper;
import com.sfd.expense_management.auth.otp.OtpPurpose;
import com.sfd.expense_management.auth.otp.OtpService;
import com.sfd.expense_management.notifications.NotificationService;
import com.sfd.expense_management.notifications.NotificationType;
import com.sfd.expense_management.notifications.dto.NotificationDto;
import com.sfd.expense_management.role.Role;
import com.sfd.expense_management.role.RoleRepository;
import com.sfd.expense_management.user.dtos.ChangePasswordRequestPayload;
import com.sfd.expense_management.user.dtos.ForgetPasswordPayload;
import com.sfd.expense_management.user.dtos.ResetPasswordPayload;
import com.sfd.expense_management.user.dtos.UserCreatePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    private final OtpService otpService;
    private final OTPHelper otpHelper;

    @Value("${superAdminRole}")
    private String roleName;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public User create(UserCreatePayload userCreatePayload) {
        Set<Role> loggedInUserRoles = UserHelper.getLoggedInUser().getRoles();
        Optional<Role> superAdminRole = loggedInUserRoles.stream()
                .filter(role-> roleName.equals(role.getName())).findFirst();
        if(superAdminRole.isPresent()){
            User existingUser = userRepository.findByUsername(userCreatePayload.getUsername());
            if (Objects.isNull(existingUser)) {
                User user = new User();
                user.setUsername(userCreatePayload.getUsername());
                user.setFirstname(userCreatePayload.getFirstname());
                user.setLastname(userCreatePayload.getLastname());
                user.setEmail(userCreatePayload.getEmail());
                user.setPassword(passwordEncoder.encode(userCreatePayload.getPassword()));
                user.setIsActive(true);
                user.setRoles(userCreatePayload.getRoles());
                user.setEmailNotificationEnabled(true);
                return userRepository.save(user);
            }
            throw new UserException("User already exist!", HttpStatus.IM_USED.value());
        }
        throw new UserException("User don't have permission to create new user!",HttpStatus.FORBIDDEN.value());
    }

    @Override
    public User createSuperAdmin(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserException("User doesn't exist!", HttpStatus.NOT_FOUND.value()));
    }
    @Override
    public User update(Long id, UserCreatePayload userCreatePayload) {
        User user = getUserById(id);
        userHelper.updateUser(user, userCreatePayload);
        return user;
    }

    @Override
    public String delete(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public String updatePassword(ChangePasswordRequestPayload requestPayload) {
        User loggedInUser = UserHelper.getLoggedInUser();
        if(loggedInUser== null
                || !passwordEncoder.matches(requestPayload.getOldPassword(), loggedInUser.getPassword())){
            throw new UserException("Password doesn't match with current password", HttpStatus.UNAUTHORIZED.value());
        }
        if(!requestPayload.getNewPassword().equals(requestPayload.getRePassword())){
            throw new UserException("Re-entered password don't match the new password", HttpStatus.BAD_REQUEST.value());
        }
        User user = userRepository.findByUsername(requestPayload.getUsername());
        user.setPassword(passwordEncoder.encode(requestPayload.getNewPassword()));
        userRepository.save(user);
        return "Password updated successfully!";
    }

    @Override
    public String updateProfile(String username, Map<String, String> newValues) {
        User user = userRepository.findByUsername(username);
        if(newValues.containsKey("firstname")){
            user.setFirstname(newValues.get("firstname"));
        }
        if(newValues.containsKey("lastname")){
            user.setLastname(newValues.get("lastname"));
        }
        if(newValues.containsKey("email")){
            user.setEmail(newValues.get("email"));
        }
        userRepository.save(user);
        return "User Profile updated successfully!";
    }

    @Override
    public String updateNotificationSettings(String username, Map<String, Boolean> newValues) {
        User user = userRepository.findByUsername(username);
        if(newValues.containsKey("emailNotificationEnabled")){
            user.setEmailNotificationEnabled(newValues.get("emailNotificationEnabled"));
        }
        if(newValues.containsKey("smsNotificationEnabled")){
            user.setEmailNotificationEnabled(newValues.get("smsNotificationEnabled"));
        }
        if(newValues.containsKey("pushNotificationEnabled")){
            user.setEmailNotificationEnabled(newValues.get("pushNotificationEnabled"));
        }
        if(newValues.containsKey("whatsappNotificationEnabled")){
            user.setEmailNotificationEnabled(newValues.get("whatsappNotificationEnabled"));
        }
        userRepository.save(user);
        return "User Notification settings updated successfully!";
    }

    @Override
    public String sendForgetPasswordOtp(ForgetPasswordPayload forgetPasswordPayload) {
        try{
            User loggedInUser = UserHelper.getLoggedInUser();
            deleteExistingOtpIfExist(forgetPasswordPayload);
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setSentFrom(senderEmail);
            notificationDto.setSentTo(forgetPasswordPayload.getEmail());
            notificationDto.setSubject("Forget password");
            Map<String, String> model = new HashMap<>();
            model.put("username", loggedInUser.getUsername());
            String otpValue = otpHelper.otpGenerator();
            model.put("otp", otpValue);
            model.put("emailTemplate", "forgetPassword.vm");
            notificationService.sendNotification(notificationDto, NotificationType.EMAIL, model);
            saveOtp(otpValue, loggedInUser.getUsername());
            return "OTP sent to your email!";
        }catch(Exception ex){
            log.warn("Unable to send email- {}", ex.getMessage());
            return "OTP sending failed!";

        }
    }

    private void deleteExistingOtpIfExist(ForgetPasswordPayload forgetPasswordPayload) {
        OTP existingOtp = otpService.getOtpByUserNameAndByPurpose(forgetPasswordPayload.getUsername(), OtpPurpose.FORGET_PASSWORD);
        if(Objects.nonNull(existingOtp)){
            otpService.deleteByOtpId(existingOtp.getId());
        }
    }

    private void saveOtp(String otpValue, String username) {
        OTP otp = new OTP();
        otp.setOtp(otpValue);
        otp.setPurpose(OtpPurpose.FORGET_PASSWORD);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setUsername(username);
        otpService.create(otp);
    }

    @Override
    public String resetPassword(ResetPasswordPayload resetPasswordPayload) {
        OTP otpData = otpService.getOtpByUserNameAndByPurpose(resetPasswordPayload.getUsername(), OtpPurpose.FORGET_PASSWORD);
        if(!Objects.equals(otpData.getOtp(), resetPasswordPayload.getOtp())){
            throw new UserException("OTP doesn't match", HttpStatus.UNAUTHORIZED.value());
        }
        LocalDateTime expirationTime = otpData.getCreatedAt().plusMinutes(15);
        if(LocalDateTime.now().isAfter(expirationTime)){
            throw new UserException("OTP has expired, please regenerate!", HttpStatus.UNAUTHORIZED.value());
        }
        User user = userRepository.findByUsername(resetPasswordPayload.getUsername());
        user.setPassword(passwordEncoder.encode(resetPasswordPayload.getPassword()));
        userRepository.save(user);
        otpService.deleteByOtpId(otpData.getId());
        return "Password Updated successfully!";
    }



}
