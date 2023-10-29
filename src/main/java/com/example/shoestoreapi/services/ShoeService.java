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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoeService(ShoeRepository shoeRepository, JdbcTemplate jdbcTemplate) {
        this.shoeRepository = shoeRepository;
        this.jdbcTemplate = jdbcTemplate;
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


    public void setUser(int userId, int shoeId) {
        jdbcTemplate.update("INSERT INTO user_shoe(user_id, shoe_id) values (?, ?)", userId, shoeId);
    }

    public void unsetUser(int userId, int shoeId) {
        jdbcTemplate.update("DELETE FROM user_shoe where user_id = ? AND shoe_id = ?", userId, shoeId);
    }

    public List<Shoe> getShoesInBasket(int userId) {
        // select s.id, s.name, s.price, s.quantity, s.size, s.image from shoe s
        //        join user_shoe us on us.shoe_id = s.id
        //        join "user" u on u.id = us.user_id;
        return jdbcTemplate.query("""
                select s.id, s.name, s.price, s.quantity, s.size, s.image from shoe s
                        join user_shoe us on us.shoe_id = s.id
                        join "user" u on u.id = us.user_id where user_id = ?;""",new Object[]{userId}, new BeanPropertyRowMapper<>(Shoe.class));
    }
}
