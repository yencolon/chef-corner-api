package org.chefcorner.chefcorner.security;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.Post;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.exceptions.NotFoundException;
import org.chefcorner.chefcorner.repositories.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final PostRepository postRepository;

    public boolean isPostOwner(Long postId, Authentication authentication)  {
        User user = ((WebUserDetails) authentication.getPrincipal()).getUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found with ID " + postId));
        return Objects.equals(post.getUser().getId(), user.getId());
    }

}
