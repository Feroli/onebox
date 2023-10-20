package org.onebox.service;

import org.onebox.exception.CartNotFoundException;
import org.onebox.model.Cart;
import org.onebox.model.Product;
import org.onebox.repository.CartRepository;
import org.onebox.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing carts in the e-commerce application.
 */
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart() {
        return cartRepository.save(new Cart(new ArrayList<>(), LocalDateTime.now()));
    }

    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    /**
     * Adds a new product to an existing cart identified by its unique ID.
     *
     * @param cartId      The unique ID of the cart to which the product will be added.
     * @param description The description of the product to be added.
     * @param amount      The price or amount associated with the product.
     * @return The updated cart after adding the product.
     * @throws CartNotFoundException if the specified cart does not exist.
     */
    @Transactional
    public Cart addProductToCart(Long cartId, String description, double amount) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Product product = new Product(description, amount);
            product.setCart(cart);
            productRepository.save(product);

            cart.getProducts().add(product);
            return cartRepository.save(cart);
        } else {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found.");
        }
    }

    public void deleteCart(Long cartId) throws IllegalArgumentException {
        cartRepository.deleteById(cartId);
    }

    public void deleteAllCarts() {
        cartRepository.deleteAll();
    }
    public void deleteAllCarts(List<Cart> cartsToDelete) {
        cartRepository.deleteAll(cartsToDelete);
    }

    public List<Cart> getCartsCreatedBeforeDate(LocalDateTime creationDateTime) {
        return cartRepository.findByCreatedAtBefore(creationDateTime);
    }
}
