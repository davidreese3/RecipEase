package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;


@Service
public class RecipeValidator implements Validator<WebRecipe, ArrayList<String>> {
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

    public ArrayList<String> validate(WebRecipe webRecipe) {
        ArrayList<String> errors = new ArrayList<String>();
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
