package org.chefcorner.chefcorner.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.dto.response.SuccessRefreshTokenResponse;

public interface AuthServiceInterface {
    SuccessAuthenticationResponse authenticateUser(LoginUserRequest user);
    SuccessRefreshTokenResponse refreshUser(HttpServletRequest request);
    SuccessAuthenticationResponse registerUser(RegisterUserRequest user);
    void logoutUser();
    boolean isAuthenticated();
}
