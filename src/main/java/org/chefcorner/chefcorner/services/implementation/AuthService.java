package org.chefcorner.chefcorner.services.implementation;

import lombok.AllArgsConstructor;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.entities.Role;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.exceptions.UserAlreadyExistsException;
import org.chefcorner.chefcorner.repositories.RoleRepository;
import org.chefcorner.chefcorner.repositories.UserRepository;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.chefcorner.chefcorner.services.interfaces.AuthServiceInterface;
import org.chefcorner.chefcorner.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public SuccessAuthenticationResponse authenticateUser(LoginUserRequest request) {
       try{
           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
           SecurityContextHolder.getContext().setAuthentication(authentication);
           WebUserDetails userPrincipal = (WebUserDetails) authentication.getPrincipal();
           String token = jwtUtils.generateToken(userPrincipal);
           return new SuccessAuthenticationResponse(token, userPrincipal.user());
       }catch (BadCredentialsException e){
           throw new BadCredentialsException("Invalid username or password");
       }
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
        List<Role> roles = new ArrayList<>();

        role.ifPresent(roles::add);

        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }

    @Override
    public void logoutUser() {

    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

}
