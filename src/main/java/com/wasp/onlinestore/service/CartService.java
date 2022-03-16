package com.wasp.onlinestore.service;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.security.entity.Session;
import java.util.List;

public class CartService {
    private Session session;

    public CartService(Session session) {
        this.session = session;
    }

    public List<Product> getCart() {
        return session.getCart();
    }

    public boolean addToCart(Product product) {
        return getCart().add(product);
    }
}
