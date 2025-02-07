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
                validateFraction(webIngredient);
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
        if (webIngredient.getWholeNumberQuantity() <= 0 && webIngredient.getFractionQuantity().equals("0")) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' cannot have a quantity of 0. If you do not want the ingredient, please remove it.");
        }
    }

    private void validateFraction(WebIngredient webIngredient) {
        if (!dropdownValidator.isValidFraction(webIngredient.getFractionQuantity())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid fraction.");
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
