package com.wasp.onlinestore.web.controller;

import com.wasp.onlinestore.entity.CartItem;
import com.wasp.onlinestore.exception.UserNotFoundException;
import com.wasp.onlinestore.service.CartService;
import com.wasp.onlinestore.service.security.entity.Session;
import com.wasp.onlinestore.web.util.SessionFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    public static final String CART_VIEW = "cart";
    private static final String REDIRECT_PRODUCTS = "redirect:/";
    private static final String REDIRECT_CART = "redirect:/cart";
    private static final String LOGIN_VIEW = "login";
    private final CartService cartService;

    @GetMapping
    public String cart(HttpServletRequest req, ModelMap model) {
        try {
            List<CartItem> cart = SessionFetcher.getSession(req).getCart();
            model.addAttribute(CART_VIEW, cart);
            return CART_VIEW;
        } catch (UserNotFoundException e) {
            return LOGIN_VIEW;
        }
    }

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
