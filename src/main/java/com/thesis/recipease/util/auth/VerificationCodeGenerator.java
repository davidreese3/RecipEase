package com.thesis.recipease.util.auth;

import java.util.Random;

public class VerificationCodeGenerator {
    public static int generateVerificationCode() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generates a 6-digit code
    }
}
