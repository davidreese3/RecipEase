package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebRecipe;

import java.util.ArrayList;

public interface Validator<T, R> {
    R validate(T input);
}
