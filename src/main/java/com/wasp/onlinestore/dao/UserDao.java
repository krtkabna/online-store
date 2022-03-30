package com.wasp.onlinestore.dao;

import com.wasp.onlinestore.entity.User;

public interface UserDao {
    User getUserByName(String name);

    boolean saveUser(User user);
}
