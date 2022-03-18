package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.onlinestore.web.util.ProductMapper.getProductFromRequestBody;

public class CartServlet extends HttpServlet {
    private static final PageGenerator PAGE_GENERATOR = new PageGenerator();
    private CartService cartService;

    public CartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Iterable<Product> products = getSession(req).getCart();

            resp.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("products", products);

            PAGE_GENERATOR.writePage("cart.html", resp.getWriter(), pageVariables);
        } catch (UserNotFoundException e) {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = getSession(req);
        Product product = getProductFromRequestBody(req);
        boolean success = cartService.addToCart(session.getCart(), product);
        resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_CONFLICT);
    }

    private Session getSession(HttpServletRequest req) {
        return (Session) req.getAttribute("session");
    }
}
