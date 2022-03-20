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

    public String getSalt(String name) {
        return userDao.getSalt(name)
            .orElseThrow(() -> new UserNotFoundException("No user found by name: "+ name));
    }

    public Optional<User> getUserByNameAndPassword(String name, String password) {
        return userDao.getUserByNameAndPassword(name, password);
    }

    public void saveUser(User user, String salt) {
        userDao.saveUser(user, salt);
    }
}
