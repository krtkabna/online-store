package com.wasp.onlinestore.service.security.entity;

import com.wasp.onlinestore.entity.Product;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Session {

    //if Session is used outside service package, move it to entity package
    private String token;
    private LocalDateTime expireDateTime;
    private List<Product> cart;
    private Role role;
}
