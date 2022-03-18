package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;

public class AdminFilter extends SecurityFilter {
    public AdminFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.ADMIN == role;
    }
}
