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

    public ArrayList<String> validate(WebSearch webSearch) {
        ArrayList<String> errors = new ArrayList<String>();
        if (!dropdownValidator.isValidHoliday(webSearch.getHolidays())) {
            errors.add("Invalid holiday: '" + webSearch.getHolidays() + "'");
        }
        if (!dropdownValidator.isValidMealType(webSearch.getMealTypes())) {
            errors.add("Invalid meal type: '" + webSearch.getMealTypes() + "'");
        }
        if (!dropdownValidator.isValidCuisine(webSearch.getCuisines())) {
            errors.add("Invalid cuisine: '" + webSearch.getCuisines() + "'");
        }
        if (!dropdownValidator.isValidAllergen(webSearch.getAllergens())) {
            errors.add("Invalid allergen: '" + webSearch.getAllergens() + "'");
        }
        if (!dropdownValidator.isValidDietType(webSearch.getDietTypes())) {
            errors.add("Invalid diet type: '" + webSearch.getDietTypes() + "'");
        }
        if (!dropdownValidator.isValidCookingLevel(webSearch.getCookingLevels())) {
            errors.add("Invalid cooking level: '" + webSearch.getCookingLevels() + "'");
        }
        if (!dropdownValidator.isValidCookingStyle(webSearch.getCookingStyles())) {
            errors.add("Invalid cooking style: '" + webSearch.getCookingStyles() + "'");
        }
        return errors;
    }
}
