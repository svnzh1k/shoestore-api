package com.example.shoestoreapi.controllers;

import com.example.shoestoreapi.models.Shoe;
import com.example.shoestoreapi.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*")
public class BasketController {
    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{userId}")
    public List<Shoe> shoesInBasket(@PathVariable ("userId") int userId){
        return basketService.getShoesInBasket(userId);
    }


    @PostMapping("/{userId}/{shoeId}")
    public ResponseEntity<String> setOwner(@PathVariable("userId") int userId, @PathVariable ("shoeId") int shoeId){
        basketService.setUser(userId, shoeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("set");
    }


    @DeleteMapping("/{userId}/{shoeId}")
    public ResponseEntity<String> unsetOwner(@PathVariable("userId") int userId, @PathVariable ("shoeId") int shoeId){
        basketService.unsetUser(userId, shoeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("unset");
    }


    @DeleteMapping("/{userId}")
    public HttpStatus clearBasket(@PathVariable ("userId") int userId){
        basketService.clearBasket(userId);
        return HttpStatus.OK;
    }


}
