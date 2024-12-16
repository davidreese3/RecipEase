package com.thesis.recipease.util.validator;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.WebAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

@Service
public class AccountValidator {
    @Autowired
    private AppService appService;

    public AccountValidator(){}

    public boolean isAccountValid(Model model, WebAccount webAccount){
        System.out.println("Validation email: " + webAccount.getEmail());
        if(!isEmailValid(webAccount.getEmail())) {
            model.addAttribute("error", "Invalid email address.");
            return false;
        }
        if (!isPasswordValid(webAccount.getPassword())) {
            model.addAttribute("error", "Password is not strong enough. It must include:");
            List<String> passwordCriteria = Arrays.asList(
                "At least one lowercase letter",
                "At least one uppercase letter",
                "At least one special character",
                "A minimum length of 8 characters",
                "No spaces at all."
            );
            model.addAttribute("passwordCriteria",passwordCriteria);
            return false;
        }
        if(!arePasswordsMatching(webAccount.getPassword(), webAccount.getConfirmPassword())){
            model.addAttribute("error", "Passwords do not match.");
            return false;
        }
        if(!isEmailUnique(webAccount.getEmail())){
            model.addAttribute("error", "An account with this email already exists.");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)\\S{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    private boolean arePasswordsMatching(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    private boolean isEmailUnique(String email){
        return appService.getAccountByEmail(email.toLowerCase()) == null;
    }
}
