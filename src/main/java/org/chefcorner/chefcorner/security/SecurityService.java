package org.chefcorner.chefcorner.security;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.Recipe;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.exceptions.NotFoundException;
import org.chefcorner.chefcorner.repositories.RecipeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final RecipeRepository recipeRepository;

    public boolean isRecipeOwner(Long recipeId, Authentication authentication) {
        User user = ((WebUserDetails) authentication.getPrincipal()).getUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found with ID " + recipeId));
        return Objects.equals(recipe.getUser().getId(), user.getId());
    }
}
