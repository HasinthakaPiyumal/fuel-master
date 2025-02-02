package com.uokse.fuelmaster.util;

import java.security.SecureRandom;

public class CodeGeneratorFactory {
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode(int length) {
        return String.format("%0" + length + "d", random.nextInt((int) Math.pow(10, length)));
    }
}
