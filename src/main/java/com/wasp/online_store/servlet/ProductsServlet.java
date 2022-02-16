package com.wasp.online_store.servlet;

import com.wasp.online_store.model.Product;
import com.wasp.online_store.service.PageGenerator;
import com.wasp.online_store.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsServlet extends HttpServlet {
    private final ProductService productService;

    public ProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getAll();

        resp.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", products);

        String page = PageGenerator.getPage("products.html", pageVariables);
        resp.getWriter().println(page);
    }
}
