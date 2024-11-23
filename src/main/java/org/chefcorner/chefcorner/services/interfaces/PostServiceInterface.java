package org.chefcorner.chefcorner.services.interfaces;

import org.chefcorner.chefcorner.dto.request.CreatePostRequest;
import org.chefcorner.chefcorner.entities.Post;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostServiceInterface {
    List<Post> getPosts();
    Post getPostById(Long id);
    Post savePost(CreatePostRequest post, Authentication authentication);
    Post updatePost(Long id, CreatePostRequest post);
    void deletePost(Long id);
}
