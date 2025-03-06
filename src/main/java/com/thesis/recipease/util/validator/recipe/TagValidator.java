package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebTag;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagValidator implements Validator<WebRecipe, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        if (webRecipe.getHolidays() != null){ validateHolidays(webRecipe.getHolidays()); }
        if (webRecipe.getMealTypes() != null){ validateMealTypes(webRecipe.getMealTypes()); }
        if (webRecipe.getCuisines() != null){ validateCuisines(webRecipe.getCuisines()); }
        if (webRecipe.getAllergens() != null){ validateAllergens(webRecipe.getAllergens()); }
        if (webRecipe.getDietTypes() != null){ validateDietTypes(webRecipe.getDietTypes()); }
        if (webRecipe.getCookingLevels() != null){ validateCookingLevels(webRecipe.getCookingLevels()); }
        if (webRecipe.getCookingStyles() != null){ validateCookingStyles(webRecipe.getCookingStyles()); }
        return errors;
    }

    private void validateHolidays(List<WebTag> holidays) {
        for (WebTag tag : holidays) {
            if (!dropdownValidator.isValidHoliday(tag.getValue())) {
                errors.add("Invalid Holiday: '" + tag.getValue()+"'");
            }
        }
    }

    private void validateMealTypes(List<WebTag> mealTypes) {
        for (WebTag tag : mealTypes) {
            if (!dropdownValidator.isValidMealType(tag.getValue())) {
                errors.add("Invalid Meal Type: '" + tag.getValue() + "'");
            }
        }
    }

    private void validateCuisines(List<WebTag> cuisines) {
        for (WebTag tag : cuisines) {
            if (!dropdownValidator.isValidCuisine(tag.getValue())) {
                errors.add("Invalid Cuisine: '" + tag.getValue() + "'");
            }
        }
    }

    private void validateAllergens(List<WebTag> allergens) {
        for (WebTag tag : allergens) {
            if (!dropdownValidator.isValidAllergen(tag.getValue())) {
                errors.add("Invalid Allergen: '" + tag.getValue() + "'");
            }
        }
    }

    private void validateDietTypes(List<WebTag> dietTypes) {
        for (WebTag tag : dietTypes) {
            if (!dropdownValidator.isValidDietType(tag.getValue())) {
                errors.add("Invalid Diet Type: '" + tag.getValue() + "'");
            }
        }
    }

    private void validateCookingLevels(List<WebTag> cookingLevels) {
        for (WebTag tag : cookingLevels) {
            if (!dropdownValidator.isValidCookingLevel(tag.getValue())) {
                errors.add("Invalid Cooking Level: '" + tag.getValue() + "'");
            }
        }
    }

    private void validateCookingStyles(List<WebTag> cookingStyles) {
        for (WebTag tag : cookingStyles) {
            if (!dropdownValidator.isValidCookingStyle(tag.getValue())) {
                errors.add("Invalid Cooking Style: '" + tag.getValue() + "'");
            }
        }
    }
}
