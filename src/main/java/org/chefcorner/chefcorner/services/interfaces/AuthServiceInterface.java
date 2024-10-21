package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.entities.User;

public interface AuthServiceInterface {
    void authenticateUser();
    void logoutUser();
    boolean isAuthenticated();
    User registerUser(RegisterUserRequest user);
}
