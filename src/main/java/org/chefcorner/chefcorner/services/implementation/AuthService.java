package org.chefcorner.chefcorner.services.implementation;

import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.entities.Role;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.exceptions.UserAlreadyExistsException;
import org.chefcorner.chefcorner.repositories.RoleRepository;
import org.chefcorner.chefcorner.repositories.UserRepository;
import org.chefcorner.chefcorner.services.interfaces.AuthServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void authenticateUser() {

    }

    @Override
    public void logoutUser() {

    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    @Transactional
    public User registerUser(RegisterUserRequest user)  {

       if(userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())) {
           throw new UserAlreadyExistsException("User already exists");
       }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEnabled(true);
        newUser.setAdmin(false);

        Optional<Role> role = roleRepository.findByName("ROLE_USER");

        role.ifPresent(value -> newUser.getRoles().add(value));

        return userRepository.save(newUser);
    }
}
