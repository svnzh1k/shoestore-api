package com.example.shoestoreapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoeDTO {
    @NotBlank
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int quantity;

    @Max(50)
    @Min(25)
    private int size;

    private byte [] image;


}
