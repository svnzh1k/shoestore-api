package com.example.shoestoreapi.controllers;

import com.example.shoestoreapi.dto.ShoeDTO;
import com.example.shoestoreapi.models.Shoe;
import com.example.shoestoreapi.services.ShoeService;
import com.example.shoestoreapi.util.IncorrectJSONException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
    public HttpStatus saveShoe(@RequestBody @Valid ShoeDTO shoeDTO, BindingResult bindingResult) throws IncorrectJSONException {
        if (bindingResult.hasErrors()){
            StringBuilder msg = new StringBuilder();
            List <FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                msg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            throw new IncorrectJSONException(msg.toString());
        }
        Shoe shoe = modelMapper.map(shoeDTO, Shoe.class);
        shoeService.save(shoe);
        System.out.println(Arrays.toString(shoe.getImage()));
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shoe> getShoe(@PathVariable ("id") int id) throws NoSuchElementException {
        return ResponseEntity.status(HttpStatus.OK).body(shoeService.getShoe(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus deleteShoe(@PathVariable ("id") int id){
        shoeService.deleteShoe(id);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("/{id}")
    public HttpStatus updateShoe(@PathVariable ("id") int id, @RequestBody @Valid ShoeDTO shoeDTO, BindingResult bindingResult) throws IncorrectJSONException {
        if (bindingResult.hasErrors()){
            StringBuilder msg = new StringBuilder();
            List <FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                msg.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            throw new IncorrectJSONException(msg.toString());
        }
        Shoe shoe = modelMapper.map(shoeDTO, Shoe.class);
        shoeService.update(shoe, id);
        return HttpStatus.ACCEPTED;
    }

    @PostMapping("/{userId}/{shoeId}")
    public ResponseEntity<String> setOwner(@PathVariable("userId") int userId, @PathVariable ("shoeId") int shoeId){
        shoeService.setUser(userId, shoeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("set");
    }

    @DeleteMapping("/{userId}/{shoeId}")
    public ResponseEntity<String> unsetOwner(@PathVariable("userId") int userId, @PathVariable ("shoeId") int shoeId){
        shoeService.unsetUser(userId, shoeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("unset");
    }

    @GetMapping("/basket/{userId}")
    public List<Shoe> shoesInBasket(@PathVariable ("userId") int userId){
        return shoeService.getShoesInBasket(userId);
    }



}
