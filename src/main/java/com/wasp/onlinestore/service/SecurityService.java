package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SecurityService {
    private UserDao userDao;
    private List<String> tokens;
    private PasswordEncoder passwordEncoder;

    public SecurityService(UserDao userDao, List<String> tokens) {
        this.userDao = userDao;
        this.tokens = tokens;
        this.passwordEncoder = new PasswordEncoder();
    }

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

}
