package com.wasp.onlinestore.entity;

import com.wasp.onlinestore.service.security.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private Role role;
}
