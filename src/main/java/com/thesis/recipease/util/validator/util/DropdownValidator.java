package com.thesis.recipease.util.validator.util;

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

    // Tags
    private static final List<String> VALID_HOLIDAYS = Arrays.asList(
            "", "Birthday", "Christmas", "Diwali", "Easter", "Father’s Day", "Halloween",
            "Hanukkah", "Independence Day", "Kwanzaa", "Lunar New Year", "Mother’s Day",
            "New Year’s Day", "Ramadan", "St. Patrick’s Day", "Thanksgiving", "Valentine’s Day", "Other"
    );

    private static final List<String> VALID_MEAL_TYPES = Arrays.asList(
            "", "Appetizer", "Breakfast", "Brunch", "Dessert", "Dinner", "Lunch", "Snack", "Other"
    );

    private static final List<String> VALID_CUISINES = Arrays.asList(
            "", "African", "American", "Caribbean", "Chinese", "French", "Greek", "Indian",
            "Italian", "Japanese", "Mediterranean", "Mexican", "Middle Eastern", "Spanish",
            "Thai", "Vietnamese", "Other"
    );

    private static final List<String> VALID_ALLERGENS = Arrays.asList(
            "", "Dairy-Free", "Egg-Free", "Fish-Free", "Gluten-Free", "Peanut-Free", "Sesame-Free",
            "Shellfish-Free", "Soy-Free", "Tree Nut-Free", "Other"
    );

    private static final List<String> VALID_DIET_TYPES = Arrays.asList(
            "", "Keto", "Low-Carb", "Low-Fat", "Paleo", "Pescatarian", "Vegetarian", "Vegan", "Other"
    );

    private static final List<String> VALID_COOKING_LEVELS = Arrays.asList(
            "", "Novice", "Beginner", "Intermediate", "Advanced", "Expert", "Chef-Level", "Other"
    );

    private static final List<String> VALID_COOKING_STYLES = Arrays.asList(
            "", "30-Minute Meals", "Air Fryer Recipes", "Baking", "Comfort Food", "Freezer-Friendly",
            "Grilled Recipes", "Healthy", "Kid-Friendly", "Make-Ahead Recipes", "Meal Prep Friendly",
            "No-Bake Recipes", "One-Pot Recipes", "Party Food", "Quick & Easy", "Sheet Pan Meals",
            "Slow Cooker Recipes", "Other"
    );

    // Info
    public boolean isValidUnitOfYield(String unit){ return VALID_UNITS_OF_YIELD.contains(unit); }

    // Ingredients and subs
    public boolean isValidMeasurement(String measurement){ return VALID_MEASUREMENTS.contains(measurement); }
    public boolean isValidPreparation(String preparation){ return VALID_PREPARATIONS.contains(preparation); }

    // Directions
    public boolean isValidMethod(String method){ return VALID_METHODS.contains(method); }
    public boolean isValidHeatLevel(String heatLevel){ return VALID_HEAT_LEVELS.contains(heatLevel); }

    // Tags
    public boolean isValidHoliday(String holiday){ return VALID_HOLIDAYS.contains(holiday); }
    public boolean isValidMealType(String mealType){ return VALID_MEAL_TYPES.contains(mealType); }
    public boolean isValidCuisine(String cuisine){ return VALID_CUISINES.contains(cuisine); }
    public boolean isValidAllergen(String allergen){ return VALID_ALLERGENS.contains(allergen); }
    public boolean isValidDietType(String dietType){ return VALID_DIET_TYPES.contains(dietType); }
    public boolean isValidCookingLevel(String level){ return VALID_COOKING_LEVELS.contains(level); }
    public boolean isValidCookingStyle(String style){ return VALID_COOKING_STYLES.contains(style); }
}
