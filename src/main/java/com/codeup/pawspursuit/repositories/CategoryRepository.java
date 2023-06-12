package com.codeup.pawspursuit.repositories;

import com.codeup.pawspursuit.models.Category;
import com.codeup.pawspursuit.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
