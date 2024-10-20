package org.chefcorner.chefcorner.services.implementation;


import org.chefcorner.chefcorner.dto.request.RegisterUserRequest;
import org.chefcorner.chefcorner.entities.User;
import java.util.List;

public interface UserServiceInterface {
    List<User> getUsers();
    User getUser();
    User saveUser(RegisterUserRequest user);
}
