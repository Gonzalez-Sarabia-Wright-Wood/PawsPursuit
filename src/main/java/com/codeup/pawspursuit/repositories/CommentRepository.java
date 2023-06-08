package com.codeup.pawspursuit.repositories;

import com.codeup.pawspursuit.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(long id);
}
