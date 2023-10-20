package org.onebox.service;

import org.onebox.model.Cart;
import org.onebox.repository.CartRepository;
import org.onebox.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartTTLService {

    private CartService cartService;

    @Autowired
    public CartTTLService(CartService cartService) {
        this.cartService = cartService;
    }

    @Scheduled(fixedRate = 600000)
    public void removeExpiredCarts() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Cart> expiredCarts = cartService.getCartsCreatedBeforeDate(tenMinutesAgo);
        cartService.deleteAllCarts(expiredCarts);
    }
}

