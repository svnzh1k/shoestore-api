package com.example.shoestoreapi.services;

import com.example.shoestoreapi.models.Shoe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BasketService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setUser(int userId, int shoeId) {
        jdbcTemplate.update("INSERT INTO user_shoe(user_id, shoe_id) values (?, ?)", userId, shoeId);
    }

    public void unsetUser(int userId, int shoeId) {
        jdbcTemplate.update("DELETE FROM user_shoe where user_id = ? AND shoe_id = ?", userId, shoeId);
    }

    public List<Shoe> getShoesInBasket(int userId) {
        return jdbcTemplate.query("""
                select s.id, s.name, s.price, s.quantity, s.size, s.image from shoe s
                        join user_shoe us on us.shoe_id = s.id
                        join "user" u on u.id = us.user_id where user_id = ?;""",new Object[]{userId}, new BeanPropertyRowMapper<>(Shoe.class));
    }

    public void clearBasket(int userId) {
        jdbcTemplate.update("delete FROM user_shoe where user_id = ?", userId);
    }
}
