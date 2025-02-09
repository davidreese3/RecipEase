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
    private NoteValidator noteValidator;
    @Autowired
    private UserSubstitutionValidator userSubstitutionValidator;
    @Autowired
    private LinkValidator linkValidator;
    @Autowired
    private PhotoValidator photoValidator;
    @Autowired
    private TagValidator tagValidator;
    private ArrayList<String> errors;

    public ArrayList<String> isRecipeValid(Model model, WebRecipe webRecipe) {
        errors = new ArrayList<String>();
        errors.addAll(infoValidator.validate(webRecipe));
        errors.addAll(ingredientValidator.validate(webRecipe));
        errors.addAll(directionValidator.validate(webRecipe));
        errors.addAll(noteValidator.validate(webRecipe));
        errors.addAll(userSubstitutionValidator.validate(webRecipe));
        errors.addAll(linkValidator.validate(webRecipe));
        errors.addAll(photoValidator.validate(webRecipe));
        errors.addAll(tagValidator.validate(webRecipe));
        return errors;
    }
}
