package org.chefcorner.chefcorner.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.services.implementation.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserRequest user) throws Exception  {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequest user) throws Exception  {
        return ResponseEntity.ok(authService.authenticateUser(user));
    }

}
