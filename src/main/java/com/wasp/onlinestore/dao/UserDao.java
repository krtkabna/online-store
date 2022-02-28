package com.wasp.onlinestore.dao;

public interface UserDao {
    boolean userExists(String name, String password);
}
