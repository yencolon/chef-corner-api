package org.chefcorner.chefcorner.services.interfaces;


import org.chefcorner.chefcorner.entities.User;
import java.util.List;

public interface UserServiceInterface {
    List<User> getUsers();
    User getUser();
}
