package com.wasp.onlinestore.service.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordEncoder {
    private static final Random RANDOM = new SecureRandom();
    private final MessageDigest MESSAGE_DIGEST;

    public PasswordEncoder() {
        try {
            MESSAGE_DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (
            NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    public String hashPassword(String password) {
        String salt = getSalt();
        MESSAGE_DIGEST.update(password.getBytes());
        MESSAGE_DIGEST.update(salt.getBytes());
        byte[] hash = MESSAGE_DIGEST.digest();
        return new String(hash, StandardCharsets.UTF_8);
    }

    private String getSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
