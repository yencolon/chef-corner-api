package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.dto.request.CreateRecipeRequest; // Consider renaming this DTO if it deals specifically with recipes
import org.chefcorner.chefcorner.entities.Recipe;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RecipeServiceInterface {
    List<Recipe> getRecipes(); // Renamed from getPosts
    Recipe getRecipeById(Long id); // Renamed from getPostById
    Recipe saveRecipe(CreateRecipeRequest recipeRequest, Authentication authentication); // Renamed from savePost
    Recipe updateRecipe(Long id, CreateRecipeRequest recipeRequest); // Renamed from updatePost
    void deleteRecipe(Long id); // Renamed from deletePost
}
