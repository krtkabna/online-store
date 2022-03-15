package com.wasp.onlinestore.service.security.entity;

import com.wasp.onlinestore.entity.Product;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
    //if Session is used outside service package, move it to entity package
    private String token;
    private LocalDateTime expireDateTime;
    private List<Product> cart;
    private Role role;

    public Session(String token, LocalDateTime localDateTime, Role userRole) {
        this.token = token;
        this.expireDateTime = localDateTime;
        this.role = userRole;
        this.cart = new ArrayList<>();
    }
}
