package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.exception.ProductParseException;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.PageGenerator;
import com.wasp.onlinestore.web.util.ProductMapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductAddServlet extends HttpServlet {
    private final ProductService productService;
    private final List<String> tokens;

    public ProductAddServlet(ProductService productService, List<String> tokens) {
        this.productService = productService;
        this.tokens = tokens;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        boolean valid = false;
        for (Cookie cookie : cookies) {
            if ("user_token".equals(cookie.getName())) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            resp.sendRedirect("/login");
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        PageGenerator.writePage("add.html", resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Product product = ProductMapper.getProductFromFields(req);
            boolean success = false;
            if (product != null) {
                success = productService.save(product);
            }

            resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect("/");
        } catch (ProductParseException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
