package com.wasp.onlinestore.web.util;

import com.wasp.onlinestore.service.security.entity.Session;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SessionFetcher {
    public Session getSession(HttpServletRequest req) {
        return (Session) req.getAttribute("session");
    }
}
