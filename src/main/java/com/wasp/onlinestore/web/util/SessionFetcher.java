package com.wasp.onlinestore.web.util;

import lombok.experimental.UtilityClass;
import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class SessionFetcher {

    public String getUserToken(Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> "user-token".equals(cookie.getName()))
            .map(Cookie::getValue)
            .collect(Collectors.joining());
    }
}