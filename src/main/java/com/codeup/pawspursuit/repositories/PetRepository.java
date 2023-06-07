package com.codeup.pawspursuit.repositories;

import com.codeup.pawspursuit.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository <Pet, Long> {
    Pet findFirstByOrderByIdDesc();
}
