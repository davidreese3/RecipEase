package com.thesis.recipease.util.formatter;

import java.text.DecimalFormat;

public class QuantityFormatter {

    public static String formatQuantity(Double quantity) {
        if (quantity == null) {
            return "";
        }
        int whole = quantity.intValue();
        double fraction = quantity - whole;

        String fractionPart = "";
        if (fraction != 0) {
            fractionPart = convertToFraction(fraction);
        }

        return (whole > 0 ? whole : "") + (fractionPart.isEmpty() ? "" : " " + fractionPart).trim();
    }

    private static String convertToFraction(double fraction) {
        int denominator = 16; // Use 16th as the maximum resolution for fractions
        int numerator = (int) Math.round(fraction * denominator);
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;

        return (numerator == denominator) ? "1" : numerator + "/" + denominator;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
