package com.wasp.onlinestore.service;

import com.wasp.onlinestore.entity.CartItem;
import com.wasp.onlinestore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class CartService {
    private final ProductService productService;

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(Map<Product, Integer> cart, int productId) {
        Product product = productService.getById(productId);
        Integer result = cart.computeIfPresent(product, (k, v) -> v + 1);
        if (result == null) {
            cart.put(product, 1);
        }
    }

    public void addToCart(List<CartItem> cart, int id) {
        Product product = productService.getById(id);
        cart.stream()
            .filter(cartContainsProduct(product))
            .findFirst()
            .ifPresentOrElse(
                incrementQuantity(),
                () -> cart.add(new CartItem(product, 1)));
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

    public void removeFromCart(List<CartItem> cart, int id) {
        Product product = productService.getById(id);
        cart.stream()
            .filter(cartContainsProduct(product))
            .findFirst()
            .ifPresent(decrementQuantityOrRemoveFromCart(cart));
    }

    private Predicate<CartItem> cartContainsProduct(Product product) {
        return cartItem -> product.equals(cartItem.getProduct());
    }

    private Consumer<CartItem> incrementQuantity() {
        return cartItem -> cartItem.setQuantity(cartItem.getQuantity() + 1);
    }

    private Consumer<CartItem> decrementQuantityOrRemoveFromCart(List<CartItem> cart) {
        return cartItem -> {
            int quantity = cartItem.getQuantity();
            if (quantity > 1) {
                cartItem.setQuantity(quantity - 1);
            } else {
                cart.remove(cartItem);
            }
        };
    }
}
