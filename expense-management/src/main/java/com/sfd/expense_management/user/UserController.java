package com.sfd.expense_management.user;

import com.sfd.expense_management.notifications.NotificationService;
import com.sfd.expense_management.user.dtos.ChangePasswordRequestPayload;
import com.sfd.expense_management.user.dtos.ForgetPasswordPayload;
import com.sfd.expense_management.user.dtos.ResetPasswordPayload;
import com.sfd.expense_management.user.dtos.UserCreatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreatePayload userCreatePayload){
        return ResponseEntity.ok(userService.create(userCreatePayload));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserList(){
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id,@RequestBody UserCreatePayload userCreatePayload){
        return ResponseEntity.ok(userService.update(id, userCreatePayload));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam Long id){
        return ResponseEntity.ok(userService.delete(id));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordRequestPayload requestPayload){
        return ResponseEntity.ok(userService.updatePassword(requestPayload));
    }

    @PutMapping("/{username}/updateProfile")
    public ResponseEntity<String> updateProfile(@PathVariable("username") String username,
                                              @RequestBody Map<String, String> newValues){
        return ResponseEntity.ok(userService.updateProfile(username, newValues));
    }

    @PutMapping("/{username}/updateNotificationSettings")
    public ResponseEntity<String> updateNotificationSettings(@PathVariable("username") String username,
                                                @RequestBody Map<String, Boolean> newValues){
        return ResponseEntity.ok(userService.updateNotificationSettings(username, newValues));
    }

    @GetMapping("/sendForgetPasswordOtp")
    public ResponseEntity<String> sendForgetPasswordOtp(@RequestBody ForgetPasswordPayload forgetPasswordPayload){
        return ResponseEntity.ok(userService.sendForgetPasswordOtp(forgetPasswordPayload));
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordPayload resetPasswordPayload){
        return ResponseEntity.ok(userService.resetPassword(resetPasswordPayload));
    }

}
