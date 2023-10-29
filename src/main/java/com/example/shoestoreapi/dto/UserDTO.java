package com.example.shoestoreapi.dto;

import jakarta.validation.constraints.*;

public class UserDTO {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    @Size(min = 2, max = 15)
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
