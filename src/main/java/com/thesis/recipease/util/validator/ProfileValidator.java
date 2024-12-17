package com.thesis.recipease.util.validator;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.WebProfile;
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
        if(!isFieldValid(webProfile.getLastName())){
            model.addAttribute("error", "Last name cannot be blank.");
            return false;
        }
        /*
        if(!isFieldValid(webProfile.getCookingLevel())){
            model.addAttribute("error", "Cooking level cannot be blank.");
            return false;
        }
        if(!isFieldValid(webProfile.getFavoriteDish())){
            model.addAttribute("error", "Favorite dish cannot be blank.");
            return false;
        }
        if(!isFieldValid(webProfile.getFavoriteCuisine())){
            model.addAttribute("error", "Favorite cuisine cannot be blank.");
            return false;
        }
        */
        return true;
    }

    public boolean isFieldValid(String field) {
        return field != null && !field.trim().isEmpty();
    }

}