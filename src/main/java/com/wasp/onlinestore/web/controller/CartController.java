package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private static final String REDIRECT_PRODUCTS = "redirect:/";
    private static final String REDIRECT_CART = "redirect:/cart";
    private final CartService cartService;

    @PostMapping("add")
    public String add(HttpServletRequest req, @RequestParam int id) {
        Session session = SessionFetcher.getSession(req);
        cartService.addToCart(session.getCart(), id);
        return REDIRECT_PRODUCTS;
    }

    @DeleteMapping("delete")
    public String remove(HttpServletRequest req, @RequestParam int id) {
        Session session = SessionFetcher.getSession(req);
        cartService.removeFromCart(session.getCart(), id);
        return REDIRECT_CART;
    }
}
