package com.thesis.recipease.util.validator;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.web.WebProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ProfileValidator {
    @Autowired
    private AppService appService;

    public ProfileValidator(){}

    public boolean isProfileValid(Model model, WebProfile webProfile){
        if(!isFieldValid(webProfile.getFirstName())){
            model.addAttribute("error", "First name cannot be blank.");
            return false;
        }
        if(!isFeildLengthValid(webProfile.getFirstName(), 20)) {
            model.addAttribute("error", "First name cannot be longer than 20 characters.");
            return false;
        }
        if(!isFieldValid(webProfile.getLastName())){
            model.addAttribute("error", "Last name cannot be blank.");
            return false;
        }
        if(!isFeildLengthValid(webProfile.getLastName(), 20)) {
            model.addAttribute("error", "Last name cannot be longer than 20 characters.");
            return false;
        }
        //cooking level is from drop down
        if(!isFeildLengthValid(webProfile.getFavoriteDish(), 40)) {
            model.addAttribute("error", "Favorite Dish cannot be longer than 20 characters.");
            return false;
        }
        if(!isFeildLengthValid(webProfile.getFavoriteCuisine(), 40)) {
            model.addAttribute("error", "Favorite Cuisine cannot be longer than 20 characters.");
            return false;
        }
        return true;
    }

    public boolean isFieldValid(String field) {
        return field != null && !field.trim().isEmpty();
    }
    public boolean isFeildLengthValid(String field, int characterLimit) {
        return field.length() < characterLimit;
    }

}