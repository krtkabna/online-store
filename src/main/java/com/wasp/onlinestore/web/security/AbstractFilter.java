package com.wasp.onlinestore.web.security;

import com.wasp.onlinestore.service.security.SecurityService;
import lombok.Setter;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

public abstract class AbstractFilter implements Filter {
    @Setter
    private SecurityService securityService;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        if (webApplicationContext != null) {
            setSecurityService(webApplicationContext.getBean(SecurityService.class));
        } else throw new ApplicationContextException("Could not get application context");
    }
}
