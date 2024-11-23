package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.entities.Category;

public interface CategoryServiceInterface {
    Category findCategoryById(Long id);
}
