package org.chefcorner.chefcorner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.services.implementation.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User related operations")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.getUserById(id);
    }
}
