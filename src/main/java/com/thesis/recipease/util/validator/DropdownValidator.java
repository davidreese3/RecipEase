package com.thesis.recipease.util.validator;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DropdownValidator {
    // Info
    private static final List<String> VALID_UNITS_OF_YIELD = Arrays.asList(
            "Bowls", "Cakes", "Cookies", "Cupcakes", "Finished Dishes", "Gallons",
            "Grams", "Kilograms", "Liters", "Loaves", "Milliliters", "Muffins",
            "Ounces", "Patties", "Pieces", "Pies", "Pints", "Plates", "Quarts",
            "Rolls", "Servings", "Slices"
    );

    // Ingredients and subs
    private static final List<String> VALID_MEASUREMENTS = Arrays.asList(
            "Cups", "Dashes", "Fluid Ounces", "Grams", "Kilograms", "Liters",
            "Milliliters", "Ounces", "Pinches", "Pounds", "Tablespoons", "Teaspoons", "Whole"
    );

    private static final List<String> VALID_PREPARATIONS = Arrays.asList(
            "Beaten", "Boiled", "Chopped", "Crushed", "Cubed", "Diced", "Grated",
            "Ground", "Julienned", "Mashed", "Melted", "Minced", "Peeled", "Roasted",
            "Shaken", "Sliced", "Stirred", "Toasted", "Unprepared", "Whisked", "Zested"
    );

    private static final List<String> VALID_FRACTIONAL_QUANTITIES = Arrays.asList(
            "0", "1/8", "1/4", "3/8", "1/3", "1/2", "5/8", "2/3", "3/4", "7/8"
    );

    // Directions
    private static final List<String> VALID_METHODS = Arrays.asList(
            "", "Assembling", "Baking", "Boiling", "Frying", "Grilling", "Mixing",
            "Roasting", "Searing", "Simmering", "Steaming", "Stirring", "Whisking"
    );

    private static final List<String> VALID_HEAT_LEVELS = Arrays.asList(
            "", "Low", "Medium", "High"
    );

    // Info
    public boolean isValidUnitOfYield(String unit){
        return VALID_UNITS_OF_YIELD.contains(unit);
    }

    // Ingredients and subs
    public boolean isValidMeasurement(String measurement){
        return VALID_MEASUREMENTS.contains(measurement);
    }

    public boolean isValidPreparation(String preparation){
        return VALID_PREPARATIONS.contains(preparation);
    }

    public boolean isValidFraction(String fraction){
        return VALID_FRACTIONAL_QUANTITIES.contains(fraction);
    }

    // Directions
    public boolean isValidMethod(String method){
        return VALID_METHODS.contains(method);
    }

    public boolean isValidHeatLevel(String heatLevel){
        return VALID_HEAT_LEVELS.contains(heatLevel);
    }
}
