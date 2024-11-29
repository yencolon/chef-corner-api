package org.chefcorner.chefcorner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IngredientModelRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private int quantity;
    private String unit;
}
