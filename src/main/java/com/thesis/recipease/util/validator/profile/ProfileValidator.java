package com.thesis.recipease.util.validator.profile;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.web.profile.WebProfile;
import com.thesis.recipease.util.validator.Validator;
import com.thesis.recipease.util.validator.util.DropdownValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProfileValidator implements Validator<WebProfile, ArrayList<String>> {
    @Autowired
    private AppService appService;
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ProfileValidator(){}

    public ArrayList<String> validate(WebProfile webProfile){
        errors = new ArrayList<String>();
        if(!isFieldValid(webProfile.getFirstName())){
            errors.add("First name cannot be blank.");
        }
        if(!isFeildLengthValid(webProfile.getFirstName(), 20)) {
            errors.add("First name cannot be longer than 20 characters.");
        }
        if(!isFieldValid(webProfile.getLastName())){
            errors.add("Last name cannot be blank.");
        }
        if(!isFeildLengthValid(webProfile.getLastName(), 20)) {
            errors.add("Last name cannot be longer than 20 characters.");
        }
        if(!dropdownValidator.isValidCookingLevel(webProfile.getCookingLevel())){
            errors.add("Cooking level is not a valid selection.");
        }
        if(!isFeildLengthValid(webProfile.getFavoriteDish(), 40)) {
            errors.add("Favorite Dish cannot be longer than 40 characters.");
        }
        if(!isFeildLengthValid(webProfile.getFavoriteCuisine(), 40)) {
            errors.add("Favorite Cuisine cannot be longer than 40 characters.");
        }
        return errors;
    }

    public boolean isFieldValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
    public boolean isFeildLengthValid(String field, int characterLimit) {
        return field.length() < characterLimit;
    }

}