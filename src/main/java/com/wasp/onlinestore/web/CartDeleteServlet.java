package com.wasp.onlinestore.web;

import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartDeleteServlet extends HttpServlet {
    private final CartService cartService;

    public CartDeleteServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Session session = SessionFetcher.getSession(req);
        cartService.removeFromCart(session.getCart(), id);

        resp.setStatus(HttpServletResponse.SC_OK);
        RequestDispatcher dispatcher = getServletContext()
            .getRequestDispatcher("/cart");
        dispatcher.forward(req, resp);
    }
}
