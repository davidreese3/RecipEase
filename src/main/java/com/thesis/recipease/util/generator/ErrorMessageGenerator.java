package com.thesis.recipease.util.generator;

import java.util.Random;

public abstract class ErrorMessageGenerator {
    private final String[] errors;
    private static final Random RANDOM = new Random();

    protected ErrorMessageGenerator(String[] errors) {
        this.errors = errors;
    }

    public String getError() {
        return errors[RANDOM.nextInt(errors.length)];
    }
}
