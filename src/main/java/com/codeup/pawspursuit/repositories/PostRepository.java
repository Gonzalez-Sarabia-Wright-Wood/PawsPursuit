package com.codeup.pawspursuit.repositories;

import com.codeup.pawspursuit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPetId(long petId);
}
