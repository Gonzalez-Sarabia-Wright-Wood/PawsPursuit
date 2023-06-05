package com.codeup.pawspursuit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PetRepository extends JpaRepository<Pet, Long> {
}
