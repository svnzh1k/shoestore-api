package com.example.shoestoreapi.repositories;

import com.example.shoestoreapi.models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Integer> {
    Optional <Shoe> findById(int id) throws NoSuchElementException;
}
