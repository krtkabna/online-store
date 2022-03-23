package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.entity.User;
import com.wasp.onlinestore.exception.UserNotFoundException;
import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public void saveUser(User user) {
        userDao.saveUser(user);
    }
}
