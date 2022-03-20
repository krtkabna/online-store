package com.wasp.onlinestore.service;

import com.wasp.onlinestore.entity.Product;
import java.util.Map;

public class CartService {
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(Map<Product, Integer> cart, Product product) {
        Integer result = cart.computeIfPresent(product, (k, v) -> v + 1);
        if (result == null) {
            cart.put(product, 1);
        }
        cart.get(product);
    }

    public void removeFromCart(Map<Product, Integer> cart, int id) {
        Product product = productService.getById(id);
        Integer result = cart.get(product);
        if (result == 1) {
            cart.remove(product);
        } else {
            cart.put(product, result - 1);
        }
    }
}
