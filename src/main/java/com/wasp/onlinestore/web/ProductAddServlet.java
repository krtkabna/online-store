package com.wasp.onlinestore.web;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.wasp.onlinestore.web.util.ProductPageVariablesMapper.createPageVariablesMap;
import static com.wasp.onlinestore.web.util.ProductPageVariablesMapper.getPageVariables;

public class ProductAddServlet extends HttpServlet {
    private final ProductService productService;

    public ProductAddServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = createPageVariablesMap(getPageVariables(req));

        resp.getWriter().println(PageGenerator.getPage("add.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product = ProductMapper.map(getPageVariables(req));
        boolean success = false;
        if (product != null) {
            success = productService.save(product);
        }

        resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.sendRedirect("/");
    }
}
