package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeValidator {
    @Autowired
    private InfoValidator infoValidator;
    @Autowired
    private IngredientValidator ingredientValidator;
    @Autowired
    private DirectionValidator directionValidator;
    private ArrayList<String> errors;

    public ArrayList<String> isRecipeValid(Model model, WebRecipe webRecipe) {
        errors = new ArrayList<String>();
        errors.addAll(infoValidator.validateInfo(webRecipe.getInfo()));
        errors.addAll(ingredientValidator.validateIngredients(webRecipe.getIngredients()));
        errors.addAll(directionValidator.validateDirections(webRecipe.getDirections()));
        return errors;
    }
}
