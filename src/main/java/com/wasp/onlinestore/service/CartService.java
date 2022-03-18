package com.wasp.onlinestore.service;

import com.wasp.onlinestore.entity.Product;
import java.util.List;

public class CartService {
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public boolean addToCart(List<Product> cart, Product product) {
        return cart.add(product);
    }

    public boolean removeFromCart(List<Product> cart, int id) {
        return cart.remove(productService.getById(id));
    }
}
