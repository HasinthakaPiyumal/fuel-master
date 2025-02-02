package com.uokse.fuelmaster.util;

public class PhoneNumberUtil {
    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        if (phoneNumber.length() == 10) {
            return "+94" + phoneNumber.substring(1);
        } else if (phoneNumber.length() == 9) {
            return "+94" + phoneNumber;
        } else if (phoneNumber.length() == 12) {
            return "+" + phoneNumber;
        } else {
            return phoneNumber;
        }
    }
}
