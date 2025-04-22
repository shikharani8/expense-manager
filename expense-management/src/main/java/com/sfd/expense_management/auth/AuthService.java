package com.sfd.expense_management.auth;

import com.sfd.expense_management.auth.dtos.AuthResponse;
import com.sfd.expense_management.auth.dtos.LoginRequest;
import com.sfd.expense_management.auth.dtos.SignupRequest;
import com.sfd.expense_management.auth.dtos.SignupResponse;
import com.sfd.expense_management.security.InvalidCredentialsException;
import com.sfd.expense_management.security.JwtUtils;
import com.sfd.expense_management.user.User;
import com.sfd.expense_management.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        if(Objects.isNull(userDetails)){
            throw new InvalidCredentialsException("User not registered", HttpStatus.UNAUTHORIZED.value());
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
          loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        try{
            String authToken = jwtUtils.generateJwtToken(userDetails, false);
            return AuthResponse.builder()
                    .username(userDetails.getUsername())
                    .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .token(authToken)
                    .build();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        return SignupResponse.builder()
                .username(signupRequest.getUsername()).build();
    }
}
