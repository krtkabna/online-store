package com.wasp.onlinestore.web;

import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CartAddServlet extends HttpServlet {
    private final CartService cartService;

    public CartAddServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Session session = SessionFetcher.getSession(req);
        cartService.addToCart(session.getCart(), id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
