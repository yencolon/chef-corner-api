package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.Post;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.exceptions.NotFoundException;
import org.chefcorner.chefcorner.repositories.PostRepository;
import org.chefcorner.chefcorner.repositories.RoleRepository;
import org.chefcorner.chefcorner.repositories.UserRepository;
import org.chefcorner.chefcorner.services.interfaces.UserServiceInterface;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {

        return this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID " + id));
    }

    @Override
    public List<Post> getUserPosts(Long id) {
        return this.postRepository.findByUserId(id);
    }

}
