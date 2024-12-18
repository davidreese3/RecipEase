package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.WebRecipe;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RecipeValidator {

    public RecipeValidator(){}

    public boolean isRecipeValid(Model model, WebRecipe webRecipe) {
        return true;
    }
}
