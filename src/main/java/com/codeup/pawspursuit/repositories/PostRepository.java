package com.codeup.pawspursuit.repositories;

import com.codeup.pawspursuit.models.Category;
import com.codeup.pawspursuit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPetId(long petId);
    List<Post> findPostsByPetNull();
    List<Post> findPostsByUserId(long userId);
}
