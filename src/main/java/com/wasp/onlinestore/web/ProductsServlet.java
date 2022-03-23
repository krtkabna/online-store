package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.main.ServiceLocator;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.PageGenerator;
import com.wasp.onlinestore.web.util.SessionFetcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductsServlet extends HttpServlet {
    private final ProductService productService = ServiceLocator.get(ProductService.class);
    private final PageGenerator pageGenerator = ServiceLocator.get(PageGenerator.class);

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
        Session session = SessionFetcher.getSession(req);
        return (session != null) ? session.getUser().getRole() : Role.GUEST;
    }
}
