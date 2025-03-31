package com.thesis.recipease.util.sanitizer.recipe.util;

import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

@Service
public class QuantitySanitizer implements Sanitizer<String> {
// Will be used for ingredients & user subs

    public String sanitize(String quantity) {
        quantity = quantity.replaceAll("\\s*/\\s*", "/").replaceAll("\\s+", " ").trim();

        // fraction or a whole number
        if (quantity.matches("\\d+/\\d+")) {
            int numerator = Integer.parseInt(quantity.substring(0,quantity.indexOf('/')));
            int denominator = Integer.parseInt(quantity.substring(quantity.indexOf('/')+1));

            if (numerator >= denominator) {
                int wholePart = numerator / denominator;
                int remainder = numerator % denominator;
                if (remainder == 0) {
                    return String.valueOf(wholePart);
                }
                return wholePart + " " + remainder + "/" + denominator;
            }
            return quantity;
        }
        if (quantity.matches("\\d+")) {
            return quantity;
        }

        try {
            double decimalVal = Double.parseDouble(quantity);
            if(decimalVal % 1 == 0){ return (int) decimalVal + ""; }
            String fraction = decimalToFraction(decimalVal);
            if (decimalVal > 1) {
                int wholeVal = (int) decimalVal;
                String fractionVal = decimalToFraction(decimalVal - wholeVal);
                return wholeVal + " " + fractionVal;
            }
            return fraction;
        } catch (NumberFormatException e) {
            return quantity;
        }
    }

    private String decimalToFraction(double decimal) {
        if (Math.abs(decimal - 0.0625) < 0.005) { return "1/16"; }
        if (Math.abs(decimal - 0.125) < 0.005) { return "1/8"; }
        if (Math.abs(decimal - 0.1875) < 0.005) { return "3/16"; }
        if (Math.abs(decimal - 0.25) < 0.005) { return "1/4"; }
        if (Math.abs(decimal - 0.3125) < 0.005) { return "5/16"; }
        if (Math.abs(decimal - 0.375) < 0.005) { return "3/8"; }
        if (Math.abs(decimal - 0.4375) < 0.005) { return "7/16"; }
        if (Math.abs(decimal - 0.5) < 0.005) { return "1/2"; }
        if (Math.abs(decimal - 0.5625) < 0.005) { return "9/16"; }
        if (Math.abs(decimal - 0.625) < 0.005) { return "5/8"; }
        if (Math.abs(decimal - 0.6875) < 0.005) { return "11/16"; }
        if (Math.abs(decimal - 0.75) < 0.005) { return "3/4"; }
        if (Math.abs(decimal - 0.8125) < 0.005) { return "13/16"; }
        if (Math.abs(decimal - 0.875) < 0.005) { return "7/8"; }
        if (Math.abs(decimal - 0.9375) < 0.005) { return "15/16"; }

        String decimalStr = String.valueOf(decimal);
        int decimalPlaces;
        if (decimalStr.contains(".")) {
            decimalPlaces = decimalStr.substring(decimalStr.indexOf('.') + 1).length();
        } else {
            decimalPlaces = 0;
        }

        int denominator = (int) Math.pow(10, decimalPlaces);
        int numerator = (int) Math.round(decimal * denominator);

        int gcd = gcd(numerator, denominator);
        numerator = numerator / gcd;
        denominator = denominator / gcd;

        // Limits length of num and denom
        while (numerator > 999 || denominator > 999) {
            numerator = Math.round(numerator / 10.0f);
            denominator = Math.round(denominator / 10.0f);
        }

        return numerator + "/" + denominator;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }
}
