package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductCrudController {
    private final ProductService productService;

    @Autowired
    public ProductCrudController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView showAddProductForm() {
        return new ModelAndView("add", "product", Product.builder().build());
    }

    @PostMapping("add")
    public ModelAndView save(@ModelAttribute("product") Product product) {
        productService.save(product);
        return new ModelAndView("redirect:/products");
    }

}
