package com.uokse.fuelmaster.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return new String(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        return hashPassword(inputPassword).equals(storedPassword);
    }
}
