package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;

import java.util.ArrayList;

public interface Validator {
    ArrayList<String> validate(WebRecipe webRecipe);
}
