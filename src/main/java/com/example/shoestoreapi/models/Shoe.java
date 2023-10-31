package com.example.shoestoreapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Target;

@Entity
@Table(name = "shoe")
@Getter
@Setter
public class Shoe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "size", nullable = false)
    private int size;

    @Lob
    private byte [] image;



}
