package org.onebox.controller;

import org.onebox.exception.CartNotFoundException;
import org.onebox.model.Cart;
import org.onebox.model.Product;
import org.onebox.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing carts in the e-commerce application.
 */
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Create a new cart.
     *
     * @return A ResponseEntity containing the created cart with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Cart> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart());
    }

    /**
     * Retrieve a cart by its ID.
     *
     * @param cartId The ID of the cart to retrieve.
     * @return A ResponseEntity containing the cart with a 200 OK status if found,
     * or a 404 Not Found status if the cart is not found.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId).map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Retrieve a list of all carts.
     *
     * @return A ResponseEntity containing a list of carts with a 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @PostMapping("/{cartId}/addProduct")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long cartId, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(cartService.addProductToCart(cartId, product.getDescription(), product.getAmount()));
        } catch (CartNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a cart by its ID.
     *
     * @param cartId The ID of the cart to delete.
     * @return A ResponseEntity with a 204 No Content status if the deletion is successful,
     * or a 404 Not Found status if the cart is not found or the deletion fails.
     */
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Cart> deleteCart(@PathVariable Long cartId) {
        try {
            cartService.deleteCart(cartId);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

