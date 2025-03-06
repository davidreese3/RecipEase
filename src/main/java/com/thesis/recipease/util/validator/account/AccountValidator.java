package com.thesis.recipease.util.validator.account;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.web.account.WebAccount;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.apache.el.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

@Service
public class AccountValidator implements Validator<WebAccount, ValidationResults> {
    @Autowired
    private AppService appService;
    private ValidationResults validationResults;

    public AccountValidator(){
        validationResults = new ValidationResults();
    }

    public ValidationResults validate(WebAccount webAccount){
        validationResults.clearLists();
        if(!isEmailValid(webAccount.getEmail())) {
            validationResults.addErrors("Invalid email address.");
        }
        if(!isEmailLengthValid(webAccount.getEmail())){
            validationResults.addErrors("Email cannot be longer than 50 characters.");
        }
        if(!isPasswordValid(webAccount.getPassword())){
            validationResults.initializePasswordCriteria();
        }
        if(!arePasswordsMatching(webAccount.getPassword(), webAccount.getConfirmPassword())){
            validationResults.addErrors("Passwords do not match.");
        }
        if(!isEmailUnique(webAccount.getEmail())){
            validationResults.addErrors("An account with this email already exists.");
        }
        return validationResults;
    }

    public ValidationResults validatePassword(WebAccount webAccount){
        validationResults.clearLists();
        if(!isPasswordValid(webAccount.getPassword())){
            validationResults.initializePasswordCriteria();
        }
        if(!arePasswordsMatching(webAccount.getPassword(), webAccount.getConfirmPassword())){
            validationResults.addErrors("Passwords do not match.");
        }
        return validationResults;
    }

    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isEmailLengthValid(String email){
        return email.length() < 50;
    }

    public boolean arePasswordsMatching(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    public boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)\\S{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isEmailUnique(String email){
        return appService.getAccountByEmail(email.toLowerCase()) == null;
    }
}