package org.onebox.service;

import org.onebox.model.Product;
import org.onebox.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing products in the e-commerce application.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String description, double amount) {
        return productRepository.save(new Product(description, amount));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long productId) throws IllegalArgumentException{
        productRepository.deleteById(productId);
    }

    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}

