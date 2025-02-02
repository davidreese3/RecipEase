package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebUserSubstitutionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSubstitutionValidator {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validateUserSubstitutions(List<WebUserSubstitutionEntry> webUserSubstitutionEntries){
        errors = new ArrayList<String>();
        if(webUserSubstitutionEntries != null) {
            for (WebUserSubstitutionEntry webUserSubstitutionEntry : webUserSubstitutionEntries) {
                validateOriginalComponent(webUserSubstitutionEntry);
                validateOriginalQuantity(webUserSubstitutionEntry);
                validateOriginalFraction(webUserSubstitutionEntry);
                validateOriginalMeasurement(webUserSubstitutionEntry);
                validateOriginalPreparation(webUserSubstitutionEntry);
                validateSubstitutedComponent(webUserSubstitutionEntry);
                validateSubstitutedQuantity(webUserSubstitutionEntry);
                validateSubstitutedFraction(webUserSubstitutionEntry);
                validateSubstitutedMeasurement(webUserSubstitutionEntry);
                validateSubstitutedPreparation(webUserSubstitutionEntry);
            }
        }
        return errors;
    }

    private void validateOriginalComponent(WebUserSubstitutionEntry entry) {
        if(entry.getOriginalComponent().length() < 0 || entry.getOriginalComponent() == null){
            errors.add("Each original ingredient needs a component.");
        }
        else if (entry.getOriginalComponent().length() > 45) {
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' is too long. It should be 45 characters in length at maximum.");
        }
    }

    private void validateOriginalQuantity(WebUserSubstitutionEntry entry) {
        if (entry.getOriginalWholeNumberQuantity() <= 0 && entry.getOriginalFractionQuantity().equals("0")) {
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' cannot have a quantity of 0. If you do not want the ingredient, please remove it.");
        }
    }
    private void validateOriginalFraction(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidFraction(entry.getOriginalFractionQuantity())) {
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' has an invalid fraction.");
        }
    }

    private void validateOriginalMeasurement(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidMeasurement(entry.getOriginalMeasurement())) {
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' has an invalid measurement.");
        }
    }

    private void validateOriginalPreparation(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidPreparation(entry.getOriginalPreparation())) {
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' has an invalid preparation method.");
        }
    }

    private void validateSubstitutedComponent(WebUserSubstitutionEntry entry) {
        if(entry.getSubstitutedComponent().length() < 0 || entry.getSubstitutedComponent() == null){
            errors.add("Each substituted ingredient needs a component.");
        }
        else if (entry.getSubstitutedComponent().length() > 45) {
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' is too long. It should be 45 characters in length at maximum.");
        }
    }

    private void validateSubstitutedQuantity(WebUserSubstitutionEntry entry) {
        if (entry.getSubstitutedWholeNumberQuantity() <= 0 && entry.getSubstitutedFractionQuantity().equals("0")) {
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' cannot have a quantity of 0. If you do not want the ingredient, please remove it");
        }
    }

    private void validateSubstitutedFraction(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidFraction(entry.getSubstitutedFractionQuantity())) {
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' has an invalid fraction.");
        }
    }

    private void validateSubstitutedMeasurement(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidMeasurement(entry.getSubstitutedMeasurement())) {
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' has an invalid measurement.");
        }
    }

    private void validateSubstitutedPreparation(WebUserSubstitutionEntry entry) {
        if (!dropdownValidator.isValidPreparation(entry.getSubstitutedPreparation())) {
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' has an invalid preparation method.");
        }
    }
}
