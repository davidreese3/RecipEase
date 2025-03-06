package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebRecipe;

public interface Sanitizer<T> {
    T sanitize(T input);
}
