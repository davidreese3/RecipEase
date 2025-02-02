package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;


@Service
public class RecipeValidator {
    @Autowired
    private InfoValidator infoValidator;
    @Autowired
    private IngredientValidator ingredientValidator;
    @Autowired
    private DirectionValidator directionValidator;
    @Autowired
    private UserSubstitutionValidator userSubstitutionValidator;
    @Autowired
    private TagValidator tagValidator;
    private ArrayList<String> errors;

    public ArrayList<String> isRecipeValid(Model model, WebRecipe webRecipe) {
        errors = new ArrayList<String>();
        errors.addAll(infoValidator.validateInfo(webRecipe.getInfo()));
        errors.addAll(ingredientValidator.validateIngredients(webRecipe.getIngredients()));
        errors.addAll(directionValidator.validateDirections(webRecipe.getDirections()));
        //note
        errors.addAll(userSubstitutionValidator.validateUserSubstitutions(webRecipe.getUserSubstitutionEntries()));
        //links
        errors.addAll(tagValidator.validateTags(webRecipe));
        return errors;
    }
}
