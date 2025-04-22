package com.sfd.expense_management.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfd.expense_management.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter implements AuthenticationEntryPoint {
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public boolean shouldNotFilter(HttpServletRequest request){
        return request.getRequestURI().startsWith("/public");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try
        {
            String jwtToken = jwtUtils.getJwtTokenFromRequest(request);
            jwtUtils.validate(jwtToken);
            setAuthenticationContext(request, jwtToken);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handleException(request, response, exception, HttpStatus.UNAUTHORIZED.value());
        }
    }

    private void setAuthenticationContext(HttpServletRequest request, String jwtToken) {
        String username = jwtUtils.getUsernameFromJwtToken(jwtToken);
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        handleException(request, response, authenticationException, HttpServletResponse.SC_UNAUTHORIZED);
    }

    private static void handleException(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Exception exception,
                                        int statusCode) throws IOException {
        log.error("Error: {}", exception.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", statusCode);
        body.put("error", "Unauthorized");
        body.put("message", exception.getMessage());
        body.put("path", request.getServletPath());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }


}
