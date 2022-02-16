package com.wasp.online_store.servlet;

import com.wasp.online_store.service.PageGenerator;
import com.wasp.online_store.service.ProductService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductDeleteServlet extends HttpServlet {
    ProductService productService;

    public ProductDeleteServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "");

        resp.getWriter().println(PageGenerator.getPage("delete.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productService.deleteById(id);
        resp.setStatus(HttpServletResponse.SC_OK);
        //won't redirect, need another solution
        resp.sendRedirect("/products");
    }
}
