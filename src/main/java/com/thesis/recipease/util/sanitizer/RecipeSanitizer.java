package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.*;
import com.thesis.recipease.util.validator.recipe.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeSanitizer implements Sanitizer<WebRecipe>{
    @Autowired
    private InfoSanitizer infoSanitizer;
    @Autowired
    private IngredientSanitizer ingredientSanitizer;
    @Autowired
    private DirectionSanitizer directionSanitizer;
    @Autowired
    private NoteSanitizer noteSanitizer;
    @Autowired
    private UserSubstitutionSanitizer userSubstitutionSanitizer;
    @Autowired
    private LinkSanitizer linkSanitizer;
    @Autowired
    private TagSanitizer tagSanitizer;

    public WebRecipe sanitize(WebRecipe webRecipe) {
        webRecipe = infoSanitizer.sanitize(webRecipe);
        webRecipe = ingredientSanitizer.sanitize(webRecipe);
        webRecipe = directionSanitizer.sanitize(webRecipe);
        webRecipe = noteSanitizer.sanitize(webRecipe);
        webRecipe = userSubstitutionSanitizer.sanitize(webRecipe);
        webRecipe = linkSanitizer.sanitize(webRecipe);
        webRecipe = tagSanitizer.sanitize(webRecipe);
        return webRecipe;
    }
}
