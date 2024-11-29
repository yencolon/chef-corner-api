package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.Ingredient;
import org.chefcorner.chefcorner.repositories.IngredientRepository;
import org.chefcorner.chefcorner.services.interfaces.IngredientServiceInterface;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService implements IngredientServiceInterface {

    private final IngredientRepository  ingredientRepository;

    @Override
    public Ingredient findIngredientByName(String name) {
        return this.ingredientRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        return this.ingredientRepository.save(ingredient);
    }

}
