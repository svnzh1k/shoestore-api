package com.example.shoestoreapi.repositories;

import com.example.shoestoreapi.models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Integer> {

}
