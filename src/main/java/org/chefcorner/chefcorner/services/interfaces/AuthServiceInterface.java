package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.dto.request.LoginUserRequest;
import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.dto.response.SuccessAuthenticationResponse;
import org.chefcorner.chefcorner.entities.User;

public interface AuthServiceInterface {
    SuccessAuthenticationResponse authenticateUser(LoginUserRequest user);
    User registerUser(RegisterUserRequest user);
    void logoutUser();
    boolean isAuthenticated();
}
