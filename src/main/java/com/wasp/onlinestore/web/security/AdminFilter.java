package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.SessionService;
import com.wasp.onlinestore.service.security.entity.Role;

public class AdminFilter extends SecurityFilter {
    public AdminFilter(SessionService sessionService) {
        super(sessionService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.ADMIN == role;
    }
}
