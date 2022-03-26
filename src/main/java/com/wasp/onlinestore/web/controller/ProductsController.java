package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/products"})
    public String getAll(ModelMap model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }
}