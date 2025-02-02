package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagValidator {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validateTags(WebRecipe webRecipe){
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
            if (!dropdownValidator.isValidHoliday(tag.getField())) {
                errors.add("Invalid Holiday: '" + tag.getField()+"'");
            }
        }
    }

    private void validateMealTypes(List<WebTag> mealTypes) {
        for (WebTag tag : mealTypes) {
            if (!dropdownValidator.isValidMealType(tag.getField())) {
                errors.add("Invalid Meal Type: '" + tag.getField() + "'");
            }
        }
    }

    private void validateCuisines(List<WebTag> cuisines) {
        for (WebTag tag : cuisines) {
            if (!dropdownValidator.isValidCuisine(tag.getField())) {
                errors.add("Invalid Cuisine: '" + tag.getField() + "'");
            }
        }
    }

    private void validateAllergens(List<WebTag> allergens) {
        for (WebTag tag : allergens) {
            if (!dropdownValidator.isValidAllergen(tag.getField())) {
                errors.add("Invalid Allergen: '" + tag.getField() + "'");
            }
        }
    }

    private void validateDietTypes(List<WebTag> dietTypes) {
        for (WebTag tag : dietTypes) {
            if (!dropdownValidator.isValidDietType(tag.getField())) {
                errors.add("Invalid Diet Type: '" + tag.getField() + "'");
            }
        }
    }

    private void validateCookingLevels(List<WebTag> cookingLevels) {
        for (WebTag tag : cookingLevels) {
            if (!dropdownValidator.isValidCookingLevel(tag.getField())) {
                errors.add("Invalid Cooking Level: '" + tag.getField() + "'");
            }
        }
    }

    private void validateCookingStyles(List<WebTag> cookingStyles) {
        for (WebTag tag : cookingStyles) {
            if (!dropdownValidator.isValidCookingStyle(tag.getField())) {
                errors.add("Invalid Cooking Style: '" + tag.getField() + "'");
            }
        }
    }
}
