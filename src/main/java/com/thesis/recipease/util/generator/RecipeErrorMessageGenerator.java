package com.thesis.recipease.util.generator;

public class RecipeErrorMessageGenerator {
    private static final String [] RECIPE_ERRORS = {
            "Oops! Looks like this recipe took a vacation. Try cooking up a search for something else!",
            "Yikes! This recipe has gone missing. Maybe it’s hiding in the pantry?",
            "Well, butter my biscuit! This recipe doesn’t exist. Let’s whip up another search!",
            "Hmm... seems this recipe is still in the oven. Check back later or find a new one to savor!",
            "Recipe not found! Did it get eaten already? Try searching for another tasty treat.",
            "Whoops! This recipe must have slipped off the menu. How about trying another dish?",
            "Oh no! This recipe’s on a secret mission. Explore our collection for more delicious options!",
            "Looks like this recipe has disappeared faster than a cookie from the jar! Search for something else to munch on."
    };

    private static final String [] VARIATION_ERRORS = {
            "Oops! You can't remix a recipe that doesn't exist. Try starting from scratch!",
            "Uh-oh! This recipe is missing, so variations are off the table. Maybe cook up something new?",
            "Looks like the original recipe is missing—no variations allowed on thin air!",
            "This recipe has vanished, which means variations are a no-go. Try a different dish!",
            "Trying to customize a missing recipe? That's like seasoning a meal that isn't there!",
            "Recipe not found! Unfortunately, you can’t build on something that doesn’t exist."
    };

    public static String getRecipeError(){
        return RECIPE_ERRORS[(int) Math.floor(Math.random() * RECIPE_ERRORS.length)];
    }

    public static String getVariationError(){
        return VARIATION_ERRORS[(int) Math.floor(Math.random() * VARIATION_ERRORS.length)];
    }
}
