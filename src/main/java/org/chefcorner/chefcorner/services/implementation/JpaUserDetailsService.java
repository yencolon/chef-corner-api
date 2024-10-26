package org.chefcorner.chefcorner.services.implementation;

import lombok.RequiredArgsConstructor;
import org.chefcorner.chefcorner.entities.User;
import org.chefcorner.chefcorner.repositories.UserRepository;
import org.chefcorner.chefcorner.security.WebUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("User with username %s not found", username));
        }
        User user = userOptional.get();
        return new WebUserDetails(user);
    }
}
