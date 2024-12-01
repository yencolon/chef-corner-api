package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.dto.request.CreateRecipeRequest;
import org.chefcorner.chefcorner.entities.Recipe;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeServiceInterface {
    List<Recipe> getRecipes();
    Recipe getRecipeById(Long id);
    Recipe saveRecipe(CreateRecipeRequest recipeRequest, MultipartFile image, Authentication authentication);
    Recipe updateRecipe(Long id, CreateRecipeRequest recipeRequest, MultipartFile image);
    void deleteRecipe(Long id);
}
