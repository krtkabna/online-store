package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wasp.onlinestore.web.util.SessionFetcher.*;

public class ProductsServlet extends HttpServlet {
    private final ProductService productService;
    private final PageGenerator pageGenerator;

    public ProductsServlet(ProductService productService) {
        this.productService = productService;
        this.pageGenerator = new PageGenerator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Iterable<Product> products = productService.getAll();

        resp.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);

        Role role = getUserRole(req);
        pageVariables.put("role", role.name());

        pageGenerator.writePage("products.html", resp.getWriter(), pageVariables);
    }

    private Role getUserRole(HttpServletRequest req) {
        Session session = getSession(req);
        return (session != null) ? session.getUser().getRole() : Role.GUEST;
    }
}
