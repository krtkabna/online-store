package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.entity.Role;

public class UserFilter extends SecurityFilter {

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.USER == role || Role.ADMIN == role;
    }
}