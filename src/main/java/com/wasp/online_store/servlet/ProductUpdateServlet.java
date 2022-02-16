package com.wasp.online_store.servlet;

import com.wasp.online_store.model.Product;
import com.wasp.online_store.service.PageGenerator;
import com.wasp.online_store.service.ProductService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductUpdateServlet extends HttpServlet {
    ProductService productService;

    public ProductUpdateServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = getEmptyData();

        resp.getWriter().println(PageGenerator.getPage("update.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");

        Product product = productService.createProduct(id, name, price);
        int rows = productService.update(product);
        if (rows > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.sendRedirect("/products/update");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Map<String, Object> getEmptyData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "");
        data.put("name", "");
        data.put("price", "");
        return data;
    }
}
