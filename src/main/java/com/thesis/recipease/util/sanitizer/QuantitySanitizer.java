package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

@Service
public class QuantitySanitizer{
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
        // Handle 1/3 and 2/3
        if (Math.abs(decimal - 0.333) < 0.005) { return "1/3"; }
        if (Math.abs(decimal - 0.666) < 0.005) { return "2/3"; }

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
