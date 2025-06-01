package com.sfd.expense_management.user;

import com.sfd.expense_management.user.dtos.ForgetPasswordPayload;
import com.sfd.expense_management.user.dtos.ResetPasswordPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/user")
public class PublicUserController {
    private final UserService userService;

    @GetMapping("/sendForgetPasswordOtp")
    public ResponseEntity<String> sendForgetPasswordOtp(@RequestBody ForgetPasswordPayload forgetPasswordPayload){
        return ResponseEntity.ok(userService.sendForgetPasswordOtp(forgetPasswordPayload));
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordPayload resetPasswordPayload){
        return ResponseEntity.ok(userService.resetPassword(resetPasswordPayload));
    }
}
