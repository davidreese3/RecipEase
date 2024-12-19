package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.WebRecipeInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RecipeValidator {

    public RecipeValidator(){}

    public boolean isRecipeValid(Model model, WebRecipeInfo webRecipeInfo) {
        return true;
    }
}
