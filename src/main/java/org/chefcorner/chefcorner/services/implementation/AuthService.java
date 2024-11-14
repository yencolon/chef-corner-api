package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.LogoutResponse;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.dto.response.SuccessRefreshTokenResponse;
import org.chefcorner.chefcorner.entities.Role;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.entities.WhiteListToken;
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
@RequiredArgsConstructor
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

           return generateSuccessAuthenticationResponse(userPrincipal);
       }catch (BadCredentialsException e){
           throw new BadCredentialsException("Invalid username or password");
       }catch (Exception e){
           throw new RuntimeException("An error occurred while authenticating the user");
       }

    }

    @Override
    @Transactional
    public SuccessAuthenticationResponse registerUser(RegisterUserRequest user)  {

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
        userRepository.save(newUser);
        WebUserDetails userPrincipal = new WebUserDetails(newUser);

        return generateSuccessAuthenticationResponse(userPrincipal);
    }


    @Override
    public SuccessRefreshTokenResponse refreshUser(Authentication authentication) {
        WebUserDetails userPrincipal = (WebUserDetails) authentication.getPrincipal();

        String refreshToken = userPrincipal.getDBRefreshToken();
        String accessToken = jwtUtils.generateAccessToken(userPrincipal);
        saveWhiteListToken(userPrincipal.getUser(), accessToken, refreshToken);

        return new SuccessRefreshTokenResponse(accessToken, refreshToken);
    }

    @Override
    public LogoutResponse logoutUser(Authentication authentication) {
        WebUserDetails userPrincipal = (WebUserDetails) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        user.setWhiteListToken(null);
        userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(null);
        return new LogoutResponse("User logged out successfully");
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    private SuccessAuthenticationResponse generateSuccessAuthenticationResponse(WebUserDetails userPrincipal) {
        String accessToken = jwtUtils.generateAccessToken(userPrincipal);
        String refreshToken = jwtUtils.generateRefreshToken(userPrincipal);
        saveWhiteListToken(userPrincipal.getUser(), accessToken, refreshToken);
        return new SuccessAuthenticationResponse(accessToken, refreshToken, userPrincipal.getUser());
    }

    private void saveWhiteListToken(User user, String accessToken, String refreshToken) {

        WhiteListToken whiteListToken = new WhiteListToken();
        whiteListToken.setAccessToken(accessToken);
        whiteListToken.setRefreshToken(refreshToken);

        user.setWhiteListToken(whiteListToken);
        userRepository.save(user);
    }
}
