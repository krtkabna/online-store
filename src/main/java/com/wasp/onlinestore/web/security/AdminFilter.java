package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import com.wasp.onlinestore.service.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.annotation.WebFilter;

@WebFilter("/product/*")
@Component
public class AdminFilter extends SecurityFilter {

    @Autowired
    public AdminFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.ADMIN == role;
    }
}