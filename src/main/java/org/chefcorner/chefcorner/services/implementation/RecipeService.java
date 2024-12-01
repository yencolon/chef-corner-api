package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chefcorner.chefcorner.dto.request.CreateRecipeRequest;
import org.chefcorner.chefcorner.entities.*;
import org.chefcorner.chefcorner.repositories.CategoryRepository;
import org.chefcorner.chefcorner.repositories.RecipeRepository;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.chefcorner.chefcorner.services.interfaces.RecipeServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService implements RecipeServiceInterface {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientService ingredientService;
    private final FileStorageService fileStorageService;

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
    public Recipe saveRecipe(CreateRecipeRequest recipeRequest, MultipartFile image, Authentication authentication) {
        // Create new recipe
        User user = ((WebUserDetails)authentication.getPrincipal()).getUser();
        Recipe newRecipe = new Recipe();
        newRecipe.setUser(user);
        populatedRecipe(newRecipe, recipeRequest);
        uploadImage(newRecipe, image);
        return this.recipeRepository.save(newRecipe);
    }

    @PreAuthorize("@securityService.isRecipeOwner(#id, authentication)")
    public Recipe updateRecipe(Long id, CreateRecipeRequest recipeRequest, MultipartFile image) {
        // Update recipe
        Recipe recipeToUpdate = this.recipeRepository.findById(id).orElseThrow();
        populatedRecipe(recipeToUpdate, recipeRequest);
        uploadImage(recipeToUpdate, image);
        return this.recipeRepository.save(recipeToUpdate);
    }

    @PreAuthorize("@securityService.isRecipeOwner(#id, authentication)")
    public void deleteRecipe(Long id) {
        this.recipeRepository.deleteById(id);
    }

    private void populatedRecipe(Recipe recipe, CreateRecipeRequest recipeRequest) {

        recipe.setTitle(recipeRequest.getTitle());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setPublished(recipeRequest.isPublished());
        recipe.setDraft(recipeRequest.isDraft());

        if (recipeRequest.isDraft()) recipe.setDraftedAt(System.currentTimeMillis());

        if (recipeRequest.isPublished()) recipe.setPublishedAt(System.currentTimeMillis());

        Category category = categoryRepository.findById(recipeRequest.getCategoryId()).orElse(null);
        recipe.setCategory(category);

        populatedIngredients(recipe, recipeRequest);
        populatedRecipeSteps(recipe, recipeRequest);
    }

    private void populatedIngredients(Recipe recipe, CreateRecipeRequest recipeRequest) {
        if(!recipe.getRecipeIngredients().isEmpty())
            recipe.getRecipeIngredients().clear();

        recipeRequest.getIngredients().forEach(ingredient -> {
            Ingredient existingIngredient = this.ingredientService.findIngredientByName(ingredient.getName());

            if (existingIngredient == null) {
                existingIngredient = new Ingredient();
                existingIngredient.setName(ingredient.getName());
                this.ingredientService.saveIngredient(existingIngredient);
            }

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setIngredient(existingIngredient);
            recipeIngredient.setQuantity(ingredient.getQuantity());
            recipeIngredient.setUnit(ingredient.getUnit());
            recipe.addRecipeIngredient(recipeIngredient);
        });
    }

    private void populatedRecipeSteps(Recipe recipe, CreateRecipeRequest recipeRequest) {
        if(!recipe.getRecipeSteps().isEmpty())
            recipe.getRecipeSteps().clear();

        recipeRequest.getRecipeSteps().forEach(recipeStep -> {
            RecipeStep newRecipeStep = new RecipeStep();
            newRecipeStep.setTitle(recipeStep.getTitle());
            newRecipeStep.setContent(recipeStep.getContent());
            newRecipeStep.setStepOrder(recipeStep.getOrder());
            recipe.addRecipeStep(newRecipeStep);
        });
    }

    private void uploadImage(Recipe recipe, MultipartFile image) {
        if (image == null) {
            return;
        }
        try {
            String imagePath = fileStorageService.storeFile(image);
            recipe.setImage(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
