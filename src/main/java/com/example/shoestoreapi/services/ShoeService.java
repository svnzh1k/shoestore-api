package com.example.shoestoreapi.services;

import com.example.shoestoreapi.models.Shoe;
import com.example.shoestoreapi.repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Shoe getShoe(int id) throws NoSuchElementException {
        return shoeRepository.findById(id).get();
    }

    public void deleteShoe(int id){
        shoeRepository.deleteById(id);
    }

    public void updateShoe(int id){
        shoeRepository.updateShoeById(id);
    }

}
