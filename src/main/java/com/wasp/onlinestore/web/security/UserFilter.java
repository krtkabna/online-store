package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;

public class UserFilter extends SecurityFilter {
    public UserFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.USER == role || Role.ADMIN == role;
    }
}
