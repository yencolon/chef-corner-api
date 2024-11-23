package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chefcorner.chefcorner.entities.Category;
import org.chefcorner.chefcorner.repositories.CategoryRepository;
import org.chefcorner.chefcorner.services.interfaces.CategoryServiceInterface;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements CategoryServiceInterface {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }
}
