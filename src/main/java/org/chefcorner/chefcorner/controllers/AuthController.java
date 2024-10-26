package org.chefcorner.chefcorner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.dto.response.SuccessRefreshTokenResponse;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.services.implementation.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication related operations")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserRequest user) throws Exception  {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<SuccessAuthenticationResponse> login(@Valid @RequestBody LoginUserRequest user) throws Exception  {
        return ResponseEntity.ok(authService.authenticateUser(user));
    }

    @Operation(summary = "Refresh a user token")
    @PostMapping("/toke/refresh")
    public ResponseEntity<SuccessRefreshTokenResponse> refresh(HttpServletRequest request) throws Exception  {
        return ResponseEntity.ok(authService.refreshUser(request));
    }

}
