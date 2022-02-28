package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import java.util.List;

public class SecurityService {
    private UserDao userDao;
    private List<String> tokens;

    public SecurityService(UserDao userDao, List<String> tokens) {
        this.userDao = userDao;
        this.tokens = tokens;
    }

    public boolean userExists(String name, String password) {
        return userDao.userExists(name, password);
    }

}
