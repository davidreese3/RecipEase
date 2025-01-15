package com.thesis.recipease.util.thymeleaf;

public class RecipeUtils {
    public static String formatTimeLong(String type, int hours, int minutes) {
        String result = type + ": ";
        if (hours > 0) {
            result += hours + " Hour";
            if (hours > 1) {
                result += "s";
            }
            result += " ";
        }
        if (minutes > 0 || hours == 0) { // Ensure minutes are included if hours are 0
            result += minutes + " Minute";
            if (minutes != 1) {
                result += "s";
            }
        }
        return result;
    }

    public static String formatTimeShort(int hours, int minutes) {
        String result = "";
        if (hours > 0) {
            result += hours + " Hr";
            if (hours > 1) {
                result += "s";
            }
            result += " ";
        }
        if (minutes > 0 || hours == 0) {
            result += minutes + " Min";
            if (minutes != 1) {
                result += "s";
            }
        }
        return result;
    }

    public static String formatIngredient(int wholeNumberQuantity, String fractionQuantity, String measurement, String preparation, String component) {
        String result = "";
        if (wholeNumberQuantity != 0) {
            result += wholeNumberQuantity + " ";
        }
        if (!"0".equals(fractionQuantity)) {
            result += fractionQuantity + " ";
        }
        result += measurement + " " + preparation + " " + component;
        return result.trim();
    }

    public static String formatSubs(int wholeNumberQuantity, String fractionQuantity, String measurement, String preparation) {
        String result = "";
        if ("0".equals(fractionQuantity)) {
            if (wholeNumberQuantity != 0) {
                result += wholeNumberQuantity;
            }
        }
        else if (wholeNumberQuantity == 0) {
            result += fractionQuantity;

        }
        else {
            result += wholeNumberQuantity + " " + fractionQuantity;
        }
        result += " " + measurement + " " + preparation;
        return result.trim();
    }

    public String formatRatingLong(double averageRating, int numberOfRaters) {
        if (averageRating > 0) {
            String formattedRating;
            if (averageRating % 1 == 0) {
                formattedRating = String.format("%.0f", averageRating);
            } else {
                formattedRating = String.format("%.1f", averageRating);
            }
            return "Average Rating: " + formattedRating + " (" + numberOfRaters + ")";
        } else {
            return "This recipe has not been rated by any users";
        }
    }

    public String formatRatingShort(double averageRating, int numberOfRaters) {
        if (averageRating > 0) {
            String formattedRating;
            if (averageRating % 1 == 0) {
                formattedRating = String.format("%.0f", averageRating);
            } else {
                formattedRating = String.format("%.1f", averageRating);
            }
            return formattedRating + " (" + numberOfRaters + ")";
        } else {
            return "0 (0)";
        }
    }

    public String formatDirectionDetails(String method, String level, int temp) {
        if (!method.isEmpty() && level.isEmpty() && temp > 0) {
            return method + " at " + temp + "°F";
        } else if (!method.isEmpty() && !level.isEmpty() && temp == 0) {
            return method + " at " + level;
        } else if (!method.isEmpty() && !level.isEmpty() && temp > 0) {
            return method + " at " + temp + "°F or " + level;
        } else if (!method.isEmpty() && temp == 0 && level.isEmpty()) {
            return method;
        }
        else {
            if (method.isEmpty() && level.isEmpty() && temp > 0) {
                return temp + "°F";
            }
            if(method.isEmpty() && !level.isEmpty() && temp == 0){
                return level;
            }
            if (method.isEmpty() && !level.isEmpty() && temp > 0) {
                return temp + "°F or " + level;
            }
        }
        return "";
    }
}
