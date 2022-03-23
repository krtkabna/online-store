package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.CartItem;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.web.util.PageGenerator;
import com.wasp.onlinestore.web.util.SessionFetcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private static final PageGenerator PAGE_GENERATOR = new PageGenerator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<CartItem> cart = SessionFetcher.getSession(req).getCart();

            resp.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("cart", cart);

            PAGE_GENERATOR.writePage("cart.html", resp.getWriter(), pageVariables);
        } catch (UserNotFoundException e) {
            resp.sendRedirect("/login");
        }
    }
}
