package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.annotation.WebFilter;

@WebFilter("/cart")
@Component
public class UserFilter extends SecurityFilter {

    @Autowired
    public UserFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.USER == role || Role.ADMIN == role;
    }
}