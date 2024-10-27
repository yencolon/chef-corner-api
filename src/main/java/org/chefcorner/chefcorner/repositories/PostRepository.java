package org.chefcorner.chefcorner.repositories;

import org.chefcorner.chefcorner.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
