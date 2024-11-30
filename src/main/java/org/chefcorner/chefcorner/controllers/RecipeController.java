package org.chefcorner.chefcorner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.dto.request.CreateRecipeRequest;
import org.chefcorner.chefcorner.entities.Recipe;
import org.chefcorner.chefcorner.services.implementation.RecipeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes") // Update the route to reflect recipes
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Recipe related operations")
public class RecipeController {

    private final RecipeService recipeService; // Update service reference

    @Operation(summary = "Get all recipes")
    @GetMapping
    public List<Recipe> getRecipes() {
        return this.recipeService.getRecipes(); // Use updated service method
    }

    @Operation(summary = "Create a new recipe")
    @PostMapping
    public Recipe createRecipe(@Valid @RequestBody CreateRecipeRequest recipeRequest, Authentication authentication) {
        return this.recipeService.saveRecipe(recipeRequest, authentication); // Use updated service method
    }

    @Operation(summary = "Get recipe by ID")
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        return this.recipeService.getRecipeById(id); // Use updated service method
    }

    @Operation(summary = "Update a recipe")
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable("id") Long id, @Valid @RequestBody CreateRecipeRequest recipeRequest) {
        return this.recipeService.updateRecipe(id, recipeRequest); // Use updated service method
    }

    @Operation(summary = "Delete a recipe")
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable("id") Long id) {
        this.recipeService.deleteRecipe(id); // Use updated service method
    }
}
