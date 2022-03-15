package com.wasp.onlinestore.service.security.entity;

public enum Role {
    GUEST, USER, ADMIN;

    public static Role getUserRole(boolean isAdmin) {
        return isAdmin ? ADMIN : USER;
    }
}
