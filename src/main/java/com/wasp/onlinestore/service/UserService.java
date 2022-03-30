package com.wasp.onlinestore.service;

import com.wasp.onlinestore.dao.UserDao;
import com.wasp.onlinestore.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User getUserByName(String name) {
        log.info("start: userService.getUserByName({})", name);
        User user = userDao.getUserByName(name);
        log.info("found user: name={}, role={}", user.getName(), user.getRole());
        log.info("end: userService.getUserByName({})", name);
        return user;
    }

    public void saveUser(User user) {
        log.info("start: userService.saveUser()");
        boolean success = userDao.saveUser(user);
        log.info("user saved: {}", success);
        log.info("end: userService.saveUser()");
    }
}
