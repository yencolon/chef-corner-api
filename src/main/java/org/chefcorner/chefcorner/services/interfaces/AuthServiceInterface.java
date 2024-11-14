package org.chefcorner.chefcorner.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.LogoutResponse;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.dto.response.SuccessRefreshTokenResponse;
import org.chefcorner.chefcorner.entities.User;
import org.springframework.security.core.Authentication;

public interface AuthServiceInterface {
    SuccessAuthenticationResponse authenticateUser(LoginUserRequest user);
    SuccessRefreshTokenResponse refreshUser(Authentication authentication);
    SuccessAuthenticationResponse registerUser(RegisterUserRequest user);
    LogoutResponse logoutUser(Authentication authentication);
    boolean isAuthenticated();
}
