package com.auth.OAuthService.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Validators {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static boolean isValidEmail(String email) {
        try {
            //validate email
            String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})+";
            if (email.matches(emailRegex)) {
                return true;
            } else {
                throw new RuntimeException("Invalid email");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate email");
        }
    }

    //validate phone
    public static boolean isValidPhone(String phone) {
        try {
            String phoneRegex = "^[+]?[0-9]{10,15}$";
            if (phone != null && phone.matches(phoneRegex)) {
                return true;
            } else {
                throw new RuntimeException("Invalid phone");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate phone");
        }
    }

    public static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean isValidPassword(String password) {
        try {
            if (password == null || password.length() < 8) {
                throw new RuntimeException("Password must be at least 8 characters long");
            }
            if (!password.matches(".*[A-Z].*")) {
                throw new RuntimeException("Password must contain at least one uppercase letter");
            }
            if (!password.matches(".*[a-z].*")) {
                throw new RuntimeException("Password must contain at least one lowercase letter");
            }
            if (!password.matches(".*\\d.*")) {
                throw new RuntimeException("Password must contain at least one digit");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to validate password: " + e.getMessage());
        }
    }

}
