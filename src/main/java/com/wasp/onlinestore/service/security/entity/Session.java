package com.wasp.onlinestore.service.security.entity;

import com.wasp.onlinestore.entity.Product;
import com.wasp.onlinestore.entity.User;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
    //if Session is used outside service package, move it to entity package
    private String token;
    private LocalDateTime expireDateTime;
    private User user;
    private List<Product> cart;

    public Session(String token, LocalDateTime localDateTime, User user) {
        this.token = token;
        this.expireDateTime = localDateTime;
        this.user = user;
        this.cart = new ArrayList<>();
    }
}
