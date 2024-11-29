package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chefcorner.chefcorner.dto.request.CreatePostRequest;
import org.chefcorner.chefcorner.entities.*;
import org.chefcorner.chefcorner.repositories.CategoryRepository;
import org.chefcorner.chefcorner.repositories.PostRepository;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.chefcorner.chefcorner.services.interfaces.PostServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements PostServiceInterface {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientService ingredientService;

    @Override
    public List<Post> getPosts() {
        return this.postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Post savePost(CreatePostRequest post, Authentication authentication) {

        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setDescription(post.getDescription());
        newPost.setPublished(post.isPublished());
        newPost.setDraft(post.isDraft());

        if(post.isDraft()) newPost.setDraftedAt(System.currentTimeMillis());

        if(post.isPublished()) newPost.setPublishedAt(System.currentTimeMillis());

        Category category = categoryRepository.findById(post.getCategoryId()).orElse(null);
        newPost.setCategory(category);

        User user = ((WebUserDetails)authentication.getPrincipal()).getUser();
        newPost.setUser(user);

        post.getIngredients().forEach(ingredient -> {
            Ingredient existingIngredient = this.ingredientService.findIngredientByName(ingredient.getName());

            if(existingIngredient == null) {
                existingIngredient = new Ingredient();
                existingIngredient.setName(ingredient.getName());
                this.ingredientService.saveIngredient(existingIngredient);
            }

            IngredientRecipe ingredientRecipe = new IngredientRecipe();
            ingredientRecipe.setIngredient(existingIngredient);
            ingredientRecipe.setQuantity(ingredient.getQuantity());
            ingredientRecipe.setUnit(ingredient.getUnit());
            newPost.addRecipeIngredient(ingredientRecipe);
        });
        return this.postRepository.save(newPost);
    }

    @PreAuthorize("@securityService.isPostOwner(#id, authentication)")
    public Post updatePost(Long id, CreatePostRequest post) {

        Post postToUpdate = new Post();
        postToUpdate.setId(id);
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setDescription(post.getDescription());
       // postToUpdate.setContent(post.getContent());
        postToUpdate.setPublished(post.isPublished());
        postToUpdate.setDraft(post.isDraft());

        return this.postRepository.save(postToUpdate);
    }

    @PreAuthorize("@securityService.isPostOwner(#id, authentication)")
    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }
}
