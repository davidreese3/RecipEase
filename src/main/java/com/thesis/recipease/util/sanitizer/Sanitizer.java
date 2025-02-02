package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebRecipe;

public interface Sanitizer {
    WebRecipe sanitize(WebRecipe webRecipe);
}
