package com.example.shoestoreapi.services;

import com.example.shoestoreapi.models.Shoe;
import com.example.shoestoreapi.repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoeService {
    private final ShoeRepository shoeRepository;


    @Autowired
    public ShoeService(ShoeRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    public List<Shoe> getAllShoes(){
        return shoeRepository.findAll();
    }

    public void save(Shoe shoe){
        shoeRepository.save(shoe);
    }

    public Shoe getShoe(int id) throws NoSuchElementException{
        Optional<Shoe> optionalShoe = shoeRepository.findById(id);
        if (optionalShoe.isEmpty()){
            throw new NoSuchElementException("No shoe with this id");
        }
        return optionalShoe.get();
    }

    public void deleteShoe(int id){
        shoeRepository.deleteById(id);
    }

    public void update(Shoe shoe, int id){
        shoe.setId(id);
        save(shoe);
    }



}
