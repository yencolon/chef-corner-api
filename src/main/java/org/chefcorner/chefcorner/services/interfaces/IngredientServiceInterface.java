package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.entities.Ingredient;

public interface IngredientServiceInterface {
    Ingredient findIngredientByName(String name);
    Ingredient saveIngredient(Ingredient ingredient);
}
