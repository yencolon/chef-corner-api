package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chefcorner.chefcorner.dto.request.CreatePostRequest;
import org.chefcorner.chefcorner.entities.Category;
import org.chefcorner.chefcorner.entities.Post;
import org.chefcorner.chefcorner.entities.User;
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
        newPost.setContent(post.getContent());
        newPost.setPublished(post.isPublished());
        newPost.setDraft(post.isDraft());

        Category category = categoryRepository.findById(post.getCategoryId()).orElse(null);

        newPost.setCategory(category);

        if(post.isDraft()) newPost.setDraftedAt(System.currentTimeMillis());

        if(post.isPublished()) newPost.setPublishedAt(System.currentTimeMillis());

        User user = ((WebUserDetails)authentication.getPrincipal()).getUser();
        newPost.setUser(user);

        return this.postRepository.save(newPost);
    }

    @PreAuthorize("@securityService.isPostOwner(#id, authentication)")
    public Post updatePost(Long id, CreatePostRequest post) {

        Post postToUpdate = new Post();
        postToUpdate.setId(id);
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
        postToUpdate.setPublished(post.isPublished());
        postToUpdate.setDraft(post.isDraft());

        return this.postRepository.save(postToUpdate);
    }

    @PreAuthorize("@securityService.isPostOwner(#id, authentication)")
    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }
}
