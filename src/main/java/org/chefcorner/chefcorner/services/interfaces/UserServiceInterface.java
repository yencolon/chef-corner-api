package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.entities.Recipe;
import org.chefcorner.chefcorner.entities.User;
import java.util.List;

public interface UserServiceInterface {
    List<User> getUsers();
    User getUser();
    User getUserById(Long id);
    List<Recipe> getUserRecipes(Long id); // Renamed from getUserPost
}
