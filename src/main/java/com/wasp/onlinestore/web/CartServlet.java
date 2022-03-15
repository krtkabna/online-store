package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.wasp.onlinestore.web.util.ProductMapper.getProductFromRequestBody;

public class CartServlet extends HttpServlet {
    private static final PageGenerator PAGE_GENERATOR = new PageGenerator();
    private final SecurityService securityService;

    public CartServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String token = getUserToken(req.getCookies());
            Iterable<Product> products = securityService.getCartByToken(token);

            resp.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("products", products);

            PAGE_GENERATOR.writePage("cart.html", resp.getWriter(), pageVariables);
        } catch (UserNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product = getProductFromRequestBody(req);
        String token = getUserToken(req.getCookies());
        boolean success = securityService.addToCart(token, product);
        resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_CONFLICT);
    }

    private String getUserToken(Cookie[] cookies) {
        Optional<String> token = Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst();
        return token.orElseThrow(() -> new UserNotFoundException("There are no authenticated users"));
    }
}
