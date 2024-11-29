package org.chefcorner.chefcorner.repositories;

import org.chefcorner.chefcorner.entities.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Optional<Ingredient> findByNameIgnoreCase(String name);
}
