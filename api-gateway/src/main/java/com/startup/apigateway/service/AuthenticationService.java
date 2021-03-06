package com.startup.apigateway.service;

import com.startup.apigateway.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.startup.apigateway.security.Roles.ROLE_USER;

@Service
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private static final int TOKEN_TTL = 60 * 60 * 24;

    @Autowired
    public AuthenticationService(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public String authenticate(String email, String password) {
        userService.validatePassword(email, password);

        return jwtProvider.generateToken(email, ROLE_USER, TOKEN_TTL);
    }
}
