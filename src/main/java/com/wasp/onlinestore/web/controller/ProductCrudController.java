package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.service.ProductService;
import com.wasp.onlinestore.web.util.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductCrudController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCrudController.class);
    public static final String REDIRECT_PRODUCTS = "redirect:/products";
    public static final String PRODUCT_ATTRIBUTE = "product";
    private final ProductService productService;

    @Autowired
    public ProductCrudController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("add")
    public ModelAndView showAddProductForm() {
        return new ModelAndView("add", PRODUCT_ATTRIBUTE, new Product());
    }

    @PostMapping("add")
    public String save(@ModelAttribute(PRODUCT_ATTRIBUTE) Product product) {
        productService.save(product);
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("update/{id}")
    public String showUpdateForm(@PathVariable int id, ModelMap model) {
        model.addAttribute(PRODUCT_ATTRIBUTE, productService.getById(id));
        return "update";
    }

    @PutMapping(value = "update")
    public String update(@RequestBody String productJson) {
        Product product = ProductMapper.getProductFromRequestBody(productJson);
        productService.update(product);
        return REDIRECT_PRODUCTS;
    }

    @DeleteMapping("delete")
    public String deleteProduct(@RequestParam int id) {
        LOGGER.debug(productService.getById(id).toString());
        productService.delete(id);
        return REDIRECT_PRODUCTS;
    }

}
