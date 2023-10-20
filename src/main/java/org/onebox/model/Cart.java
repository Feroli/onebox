package org.onebox.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Cart() {
    }

    public Cart(List<Product> products, LocalDateTime createdAt) {
        this.products = products;
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }
}