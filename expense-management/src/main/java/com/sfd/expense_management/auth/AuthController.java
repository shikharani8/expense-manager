package com.sfd.expense_management.auth;

import com.sfd.expense_management.auth.dtos.AuthResponse;
import com.sfd.expense_management.auth.dtos.LoginRequest;
import com.sfd.expense_management.auth.dtos.SignupRequest;
import com.sfd.expense_management.auth.dtos.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return  ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authService.signup(signupRequest));
    }
}
