package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import static com.wasp.onlinestore.web.util.SessionFetcher.*;

public class CartAddServlet extends HttpServlet {
    private final CartService cartService;
    private final ProductService productService;

    public CartAddServlet(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.getById(id);
        Session session = getSession(req);
        Map<Product, Integer> cart = session.getCart();
        cartService.addToCart(cart, product);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
