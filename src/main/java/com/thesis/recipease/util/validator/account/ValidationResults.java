package com.thesis.recipease.util.validator.account;


import com.thesis.recipease.util.validator.Validator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ValidationResults {
    ArrayList<String> errors;
    ArrayList<String> passwordCriteria;

    public ValidationResults(){
        errors = new ArrayList<>();
        passwordCriteria = new ArrayList<>();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public void addErrors(String item){
        errors.add(item);
    }

    public ArrayList<String> getPasswordCriteria() {
        return passwordCriteria;
    }

    public void setPasswordCriteria(ArrayList<String> passwordCriteria) {
        this.passwordCriteria = passwordCriteria;
    }

    public void initializePasswordCriteria(){
        passwordCriteria = new ArrayList<>(Arrays.asList(
                "At least one lowercase letter",
                "At least one uppercase letter",
                "At least one special character",
                "A minimum length of 8 characters",
                "No spaces at all."
        ));
    }

    public void clearLists(){
        errors.clear();
        passwordCriteria.clear();
    }
}
