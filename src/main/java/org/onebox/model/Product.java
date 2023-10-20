package org.onebox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Cart cart;
    private String description;
    private double amount;

    public Product() {
    }

    public Product(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
