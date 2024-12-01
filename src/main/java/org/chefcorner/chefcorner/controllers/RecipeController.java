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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipe", description = "Recipe related operations")
public class RecipeController {

    private final RecipeService recipeService;

    @Operation(summary = "Get all recipes")
    @GetMapping
    public List<Recipe> getRecipes() {
        return this.recipeService.getRecipes();
    }

    @Operation(summary = "Create a new recipe")
    @PostMapping(consumes = "multipart/form-data")
    public Recipe createRecipe(@RequestParam("image") MultipartFile image,
                               @Valid @RequestPart CreateRecipeRequest recipeRequest,
                               Authentication authentication) {
        return this.recipeService.saveRecipe(recipeRequest, image, authentication);
    }

    @Operation(summary = "Get recipe by ID")
    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable("id") Long id) {
        return this.recipeService.getRecipeById(id);
    }

    @Operation(summary = "Update a recipe")
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable("id") Long id,
                               @RequestParam("image") MultipartFile image,
                               @Valid @RequestBody CreateRecipeRequest recipeRequest) {
        return this.recipeService.updateRecipe(id, recipeRequest, image);
    }

    @Operation(summary = "Delete a recipe")
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable("id") Long id) {
        this.recipeService.deleteRecipe(id);
    }
}
