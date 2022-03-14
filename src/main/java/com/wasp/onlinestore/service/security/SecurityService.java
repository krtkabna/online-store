package com.wasp.onlinestore.service.security;

import com.wasp.onlinestore.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private UserDao userDao;
    private List<String> tokens;
    private PasswordEncoder passwordEncoder;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
        this.tokens = new ArrayList<>();
        this.passwordEncoder = new PasswordEncoder();
    }

    //check salted password in base
    public boolean userExists(String name, String password) {
        return userDao.userExists(name, password);
    }

    public String generateToken() {
        String token = String.valueOf(UUID.randomUUID());
        tokens.add(token);
        return token;
    }

    public boolean saveUser(String login, String password) {
        String hashedPassword = passwordEncoder.hashPassword(password);
        return userDao.saveUser(login, hashedPassword);
    }

    public boolean isTokenValid(String value) {
        return tokens.contains(value);
    }

    public String login(String login, String password) {
        if (!userExists(login, password)) {
            saveUser(login, password);
        }
        return generateToken();
    }
}
