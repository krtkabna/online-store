package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public Optional<User> getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public void saveUser(User user) {
        userDao.saveUser(user);
    }
}
