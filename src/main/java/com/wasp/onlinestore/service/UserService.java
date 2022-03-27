package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
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
