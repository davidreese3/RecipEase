package com.thesis.recipease.util.validator.recipe.util;

import com.thesis.recipease.model.web.recipe.util.WebSearch;
import com.thesis.recipease.util.validator.Validator;
import com.thesis.recipease.util.validator.util.DropdownValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SearchValidator implements Validator<WebSearch, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebSearch webSearch) {
        errors = new ArrayList<String>();
        if (!dropdownValidator.isValidHoliday(webSearch.getHolidays())) {
            errors.add("Invalid Holiday: '" + webSearch.getHolidays() + "'");
        }
        if (!dropdownValidator.isValidMealType(webSearch.getMealTypes())) {
            errors.add("Invalid Meal Type: '" + webSearch.getMealTypes() + "'");
        }
        if (!dropdownValidator.isValidCuisine(webSearch.getCuisines())) {
            errors.add("Invalid Cuisine: '" + webSearch.getCuisines() + "'");
        }
        if (!dropdownValidator.isValidAllergen(webSearch.getAllergens())) {
            errors.add("Invalid Allergen: '" + webSearch.getAllergens() + "'");
        }
        if (!dropdownValidator.isValidDietType(webSearch.getDietTypes())) {
            errors.add("Invalid Diet Type: '" + webSearch.getDietTypes() + "'");
        }
        if (!dropdownValidator.isValidCookingLevel(webSearch.getCookingLevels())) {
            errors.add("Invalid Cooking Level: '" + webSearch.getCookingLevels() + "'");
        }
        if (!dropdownValidator.isValidCookingStyle(webSearch.getCookingStyles())) {
            errors.add("Invalid Cooking Style: '" + webSearch.getCookingStyles() + "'");
        }
        return errors;
    }
}
