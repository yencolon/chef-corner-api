package org.chefcorner.chefcorner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.LogoutResponse;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.dto.response.SuccessRefreshTokenResponse;
import org.chefcorner.chefcorner.services.implementation.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication related operations")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<SuccessAuthenticationResponse> register(@Valid @RequestBody RegisterUserRequest user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<SuccessAuthenticationResponse> login(@Valid @RequestBody LoginUserRequest user) {
        return ResponseEntity.ok(authService.authenticateUser(user));
    }

    @Operation(summary = "Logout a user")
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse > logout(Authentication authentication) throws Exception {
        return ResponseEntity.ok(authService.logoutUser(authentication));
    }

    @Operation(summary = "Refresh a user token")
    @RequestMapping(value = "/refresh", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<SuccessRefreshTokenResponse> refresh(Authentication authentication) {
        return ResponseEntity.ok(authService.refreshUser(authentication));
    }

}
