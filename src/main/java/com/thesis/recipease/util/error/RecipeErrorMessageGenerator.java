package com.thesis.recipease.util.error;

import org.springframework.stereotype.Service;

@Service
public class RecipeErrorMessageGenerator {
    private String [] recipeErrors = {
            "Oops! Looks like this recipe took a vacation. Try cooking up a search for something else!",
            "Yikes! This recipe has gone missing. Maybe it’s hiding in the pantry?",
            "Well, butter my biscuit! This recipe doesn’t exist. Let’s whip up another search!",
            "Hmm... seems this recipe is still in the oven. Check back later or find a new one to savor!",
            "Recipe not found! Did it get eaten already? Try searching for another tasty treat.",
            "Whoops! This recipe must have slipped off the menu. How about trying another dish?",
            "Oh no! This recipe’s on a secret mission. Explore our collection for more delicious options!",
            "Looks like this recipe has disappeared faster than a cookie from the jar! Search for something else to munch on."
    };

    public String getRecipeError(){
        return recipeErrors[(int) Math.floor(Math.random() * 7)];
    }
}
