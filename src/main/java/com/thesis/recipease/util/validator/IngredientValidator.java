package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebIngredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngredientValidator {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validateIngredients(List<WebIngredient> webIngredients){
        errors = new ArrayList<String>();
        if(webIngredients == null){
            errors.add("Every recipe should have at least one ingredient.");
        }
        else {
            for (WebIngredient webIngredient : webIngredients) {
                validateLengthOfIngredient(webIngredient);
                validateQuantityOfIngredient(webIngredient);
                validateFractionOfIngredient(webIngredient);
                validateMeasurementOfIngredient(webIngredient);
                validatePreperationOfIngredient(webIngredient);
            }
            validateDuplicates(webIngredients);
        }
        return errors;
    }

    private void validateLengthOfIngredient(WebIngredient webIngredient){
        if(webIngredient.getComponent().length() < 0 || webIngredient.getComponent() == null){
            errors.add("Each ingredient needs a component.");
        }
        if(webIngredient.getComponent().length() > 45){
            errors.add("Ingredient \'" + webIngredient.getComponent() + "\' is too long. It should be 45 characters in length at maximum.");
        }
    }

    private void validateQuantityOfIngredient(WebIngredient webIngredient) {
        if (webIngredient.getWholeNumberQuantity() <= 0 && webIngredient.getFractionQuantity().equals("0")) {
            errors.add("Ingredient \'" + webIngredient.getComponent() + "\' cannot have a quantity of 0. If you do not want the ingredient, please remove it.");
        }
    }

    private void validateFractionOfIngredient(WebIngredient webIngredient) {
        if (!dropdownValidator.isValidFraction(webIngredient.getFractionQuantity())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid fraction.");
        }
    }

    private void validateMeasurementOfIngredient(WebIngredient webIngredient) {
        if (!dropdownValidator.isValidMeasurement(webIngredient.getMeasurement())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid measurement.");
        }
    }

    private void validatePreperationOfIngredient(WebIngredient webIngredient){
        if (!dropdownValidator.isValidPreparation(webIngredient.getPreparation())) {
            errors.add("Ingredient '" + webIngredient.getComponent() + "' has an invalid preparation method.");
        }
    }

    private void validateDuplicates(List<WebIngredient> webIngredients){
        // For multiple duplicates of same component
        Set<String> duplicateTracker = new HashSet<>();
        for (int i = 0; i < webIngredients.size(); i++) {
            String currIngredient = webIngredients.get(i).getComponent();
            for (int j = i + 1; j < webIngredients.size(); j++){
                if (currIngredient.equalsIgnoreCase(webIngredients.get(j).getComponent())){
                    if (!duplicateTracker.contains(currIngredient.toLowerCase())) {
                        errors.add("Ingredient '" + currIngredient + "' has a duplicate. Please remove duplicate");
                        duplicateTracker.add(currIngredient.toLowerCase());
                    }
                }
            }
        }
    }

}
