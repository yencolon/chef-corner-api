package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chefcorner.chefcorner.dto.request.CreateRecipeRequest; // Consider renaming this DTO if specific to recipes
import org.chefcorner.chefcorner.entities.*;
import org.chefcorner.chefcorner.repositories.CategoryRepository;
import org.chefcorner.chefcorner.repositories.RecipeRepository; // Update repository names if necessary
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.chefcorner.chefcorner.services.interfaces.RecipeServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService implements RecipeServiceInterface {

    private final RecipeRepository recipeRepository; // Renamed from RecipeRepository
    private final CategoryRepository categoryRepository;
    private final IngredientService ingredientService;

    @Override
    public List<Recipe> getRecipes() { // Renamed from getPosts
        return this.recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) { // Renamed from getPostById
        return this.recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Recipe saveRecipe(CreateRecipeRequest recipeRequest, Authentication authentication) { // Renamed from savePost

        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(recipeRequest.getTitle());
        newRecipe.setDescription(recipeRequest.getDescription());
        newRecipe.setPublished(recipeRequest.isPublished());
        newRecipe.setDraft(recipeRequest.isDraft());

        if (recipeRequest.isDraft()) newRecipe.setDraftedAt(System.currentTimeMillis());

        if (recipeRequest.isPublished()) newRecipe.setPublishedAt(System.currentTimeMillis());

        Category category = categoryRepository.findById(recipeRequest.getCategoryId()).orElse(null);
        newRecipe.setCategory(category);

        User user = ((WebUserDetails)authentication.getPrincipal()).getUser();
        newRecipe.setUser(user);

        recipeRequest.getIngredients().forEach(ingredient -> {
            Ingredient existingIngredient = this.ingredientService.findIngredientByName(ingredient.getName());

            if (existingIngredient == null) {
                existingIngredient = new Ingredient();
                existingIngredient.setName(ingredient.getName());
                this.ingredientService.saveIngredient(existingIngredient);
            }

            IngredientRecipe ingredientRecipe = new IngredientRecipe();
            ingredientRecipe.setIngredient(existingIngredient);
            ingredientRecipe.setQuantity(ingredient.getQuantity());
            ingredientRecipe.setUnit(ingredient.getUnit());
            newRecipe.addRecipeIngredient(ingredientRecipe);
        });
        return this.recipeRepository.save(newRecipe);
    }

    @PreAuthorize("@securityService.isRecipeOwner(#id, authentication)") // Updated security expression
    public Recipe updateRecipe(Long id, CreateRecipeRequest recipeRequest) { // Renamed from updatePost

        Recipe recipeToUpdate = this.recipeRepository.findById(id).orElseThrow();
        recipeToUpdate.setTitle(recipeRequest.getTitle());
        recipeToUpdate.setDescription(recipeRequest.getDescription());
        recipeToUpdate.setPublished(recipeRequest.isPublished());
        recipeToUpdate.setDraft(recipeRequest.isDraft());

        if (recipeRequest.isDraft()) recipeToUpdate.setDraftedAt(System.currentTimeMillis());

        if (recipeRequest.isPublished()) recipeToUpdate.setPublishedAt(System.currentTimeMillis());

        Category category = categoryRepository.findById(recipeRequest.getCategoryId()).orElse(null);
        recipeToUpdate.setCategory(category);

        recipeToUpdate.getRecipeIngredients().clear();

        recipeRequest.getIngredients().forEach(ingredient -> {
            Ingredient existingIngredient = this.ingredientService.findIngredientByName(ingredient.getName());

            if (existingIngredient == null) {
                existingIngredient = new Ingredient();
                existingIngredient.setName(ingredient.getName());
                this.ingredientService.saveIngredient(existingIngredient);
            }

            IngredientRecipe ingredientRecipe = new IngredientRecipe();
            ingredientRecipe.setIngredient(existingIngredient);
            ingredientRecipe.setQuantity(ingredient.getQuantity());
            ingredientRecipe.setUnit(ingredient.getUnit());
            recipeToUpdate.addRecipeIngredient(ingredientRecipe);
        });

        return this.recipeRepository.save(recipeToUpdate);
    }

    @PreAuthorize("@securityService.isRecipeOwner(#id, authentication)") // Updated security expression
    public void deleteRecipe(Long id) { // Renamed from deletePost
        this.recipeRepository.deleteById(id);
    }
}
