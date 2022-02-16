package com.wasp.online_store.servlet;

import com.wasp.online_store.service.PageGenerator;
import com.wasp.online_store.service.ProductService;
import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("id", req.getParameter("id"));

        resp.getWriter().println(PageGenerator.getPage("delete.html", data));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int rows = productService.deleteById(Integer.parseInt(req.getParameter("id")));
        if (rows > 0) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.sendRedirect("/products/delete");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
