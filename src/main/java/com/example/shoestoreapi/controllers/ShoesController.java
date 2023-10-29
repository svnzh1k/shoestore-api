package com.example.shoestoreapi.controllers;

import com.example.shoestoreapi.dto.ShoeDTO;
import com.example.shoestoreapi.models.Shoe;
import com.example.shoestoreapi.services.ShoeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoes")
public class ShoesController {
    private final ShoeService shoeService;
    private final ModelMapper modelMapper;


    @Autowired
    public ShoesController(ShoeService shoeService, ModelMapper modelMapper) {
        this.shoeService = shoeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<Shoe> getAllShoes(){
        return shoeService.getAllShoes();
    }

    @PostMapping
    public HttpStatus saveShoe(@RequestBody ShoeDTO shoeDTO){
        Shoe shoe = modelMapper.map(shoeDTO, Shoe.class);
        shoeService.save(shoe);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}")
    public Shoe getShoe(@PathVariable ("id") int id){
        return shoeService.getShoe(id);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteShoe(@PathVariable ("id") int id){
        shoeService.deleteShoe(id);
        return HttpStatus.ACCEPTED;
    }



}
