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
import java.util.Map;

public class ProductAddServlet extends HttpServlet {
    private ProductService productService;

    public ProductAddServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = createPageVariablesMap(getPageVariables(req));

        resp.getWriter().println(PageGenerator.getPage("add.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] pageVariables = getPageVariables(req);
        Product product = createProduct(pageVariables);
        boolean success = false;
        if (product != null) {
            success = productService.insert(product);
        }

        resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.sendRedirect("/products");
    }

    private String[] getPageVariables(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        return new String[]{id, name, price};
    }

    private Map<String, Object> createPageVariablesMap(String[] array) {
        Map<String, Object> map = new HashMap<>();
        for (String s : array) {
            map.put(s, "");
        }
        return map;
    }

    private Product createProduct(String[] array) {
        return productService.createProduct(array[0], array[1], array[2]);
    }
}
