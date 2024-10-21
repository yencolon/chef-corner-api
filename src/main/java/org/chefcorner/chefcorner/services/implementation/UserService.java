package org.chefcorner.chefcorner.services.implementation;

import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.entities.Role;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.repositories.RoleRepository;
import org.chefcorner.chefcorner.repositories.UserRepository;
import org.chefcorner.chefcorner.services.interfaces.UserServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser() {
        return null;
    }

}
