package org.chefcorner.chefcorner.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateRecipeRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private List<IngredientModelRequest> ingredients;
    private List<RecipeStepModelRequest> recipeSteps;
    @NotNull(message = "Category is required")
    private Long categoryId;
    private boolean published;
    private boolean draft;
}
