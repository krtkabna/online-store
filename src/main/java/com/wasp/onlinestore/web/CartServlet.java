package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.SessionService;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.onlinestore.web.util.SessionFetcher.*;

public class CartServlet extends HttpServlet {
    private static final PageGenerator PAGE_GENERATOR = new PageGenerator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Map<Product, Integer> cart = getSession(req).getCart();

            resp.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("cart", cart);

            PAGE_GENERATOR.writePage("cart.html", resp.getWriter(), pageVariables);
        } catch (UserNotFoundException e) {
            resp.sendRedirect("/login");
        }
    }
}
