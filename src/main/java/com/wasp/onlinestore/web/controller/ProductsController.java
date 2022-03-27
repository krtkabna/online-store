package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.service.security.entity.Role;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getAll(HttpServletRequest req, ModelMap model) {
        model.addAttribute("role", getUserRole(req).name());
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    private Role getUserRole(HttpServletRequest req) {
        Session session = SessionFetcher.getSession(req);
        return (session != null) ? session.getUser().getRole() : Role.GUEST;
    }
}