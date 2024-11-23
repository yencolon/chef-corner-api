package org.chefcorner.chefcorner.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.dto.request.CreatePostRequest;
import org.chefcorner.chefcorner.entities.Post;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.chefcorner.chefcorner.services.implementation.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post", description = "Post related operations")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getPosts() {
        return this.postService.getPosts();
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public Post createPost(@Valid @RequestBody CreatePostRequest post, Authentication authentication) {
        return this.postService.savePost(post, authentication);
    }

    @Operation(summary = "Get post by ID")
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return this.postService.getPostById(id);
    }

    @Operation(summary = "Update a post")
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable("id") Long id, @Valid @RequestBody CreatePostRequest post) {
        return this.postService.updatePost(id, post);
    }

    @Operation(summary = "Delete a post")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        this.postService.deletePost(id);
    }


}
