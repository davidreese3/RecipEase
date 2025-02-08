package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebInfo;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IngredientValidator implements Validator{
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        List<WebIngredient> webIngredients = webRecipe.getIngredients();
        if(webIngredients == null){
            errors.add("Every recipe should have at least one ingredient.");
        }
        else {
            Set<String> seenIngredients = new HashSet<>();
            Set<String> reportedDuplicates = new HashSet<>();

            for (WebIngredient webIngredient : webIngredients) {
                validateComponent(webIngredient);
                validateQuantity(webIngredient);
                validateMeasurement(webIngredient);
                validatePreperation(webIngredient);

                String key = webIngredient.getComponent().toLowerCase() + "|" + webIngredient.getPreparation().toLowerCase();
                if (!seenIngredients.add(key) && reportedDuplicates.add(key)) {
                    errors.add("Ingredient '" + webIngredient.getComponent() + "' has a duplicate preparation. Please remove the duplicate or change preparation.");
                }
            }
        }
        return errors;
    }

    private void validateComponent(WebIngredient webIngredient){
        if(webIngredient.getComponent().length() < 0 || webIngredient.getComponent() == null){
            errors.add("Each ingredient needs a component.");
        }
        if(webIngredient.getComponent().length() > 45){
            errors.add("Ingredient '" + webIngredient.getComponent() + "' is too long. It should be 45 characters in length at maximum.");
        }
    }

    private void validateQuantity(WebIngredient webIngredient) {
        String quantityRegex = "\\d+|\\.\\d+|\\d+\\.\\d+|\\d+\\s+\\d+/\\d+|\\d+/\\d+";
        Pattern pattern = Pattern.compile(quantityRegex);
        Matcher matcher = pattern.matcher(webIngredient.getQuantity());
        if (!matcher.matches()){
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid quantity.");
        }
        else if (webIngredient.getQuantity().length() > 10){
            errors.add("Ingredient '" + webIngredient.getComponent() + "' cannot have a quantity exceeding 10 characters.");

        }
    }

    private void validateMeasurement(WebIngredient webIngredient) {
        if (!dropdownValidator.isValidMeasurement(webIngredient.getMeasurement())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid measurement.");
        }
    }

    private void validatePreperation(WebIngredient webIngredient){
        if (!dropdownValidator.isValidPreparation(webIngredient.getPreparation())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid preparation method.");
        }
    }
}
