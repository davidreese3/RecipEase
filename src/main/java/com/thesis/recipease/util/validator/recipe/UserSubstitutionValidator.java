package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebUserSubstitutionEntry;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserSubstitutionValidator implements Validator<WebRecipe, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        List<WebUserSubstitutionEntry> webUserSubstitutionEntries = webRecipe.getUserSubstitutionEntries();
        if(webUserSubstitutionEntries != null) {
            Set<String> seenIngredients = new HashSet<>();
            Set<String> reportedDuplicates = new HashSet<>();
            for (WebUserSubstitutionEntry webUserSubstitutionEntry : webUserSubstitutionEntries) {
                validateOriginalComponent(webUserSubstitutionEntry);
                validateOriginalQuantity(webUserSubstitutionEntry);
                validateOriginalMeasurement(webUserSubstitutionEntry);
                validateOriginalPreparation(webUserSubstitutionEntry);
                validateSubstitutedComponent(webUserSubstitutionEntry);
                validateSubstitutedQuantity(webUserSubstitutionEntry);
                validateSubstitutedMeasurement(webUserSubstitutionEntry);
                validateSubstitutedPreparation(webUserSubstitutionEntry);

                String key = webUserSubstitutionEntry.getOriginalComponent().toLowerCase() + "|" + webUserSubstitutionEntry.getOriginalPreparation().toLowerCase() + "|"
                + webUserSubstitutionEntry.getSubstitutedComponent().toLowerCase() + "|" + webUserSubstitutionEntry.getSubstitutedPreparation().toLowerCase();

                if (!seenIngredients.add(key) && reportedDuplicates.add(key)) {
                    errors.add("Substitution '" + webUserSubstitutionEntry.getOriginalComponent() + "' has a duplicate ingredient and preparation. Please remove the duplicate or change preparation.");
                }
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
        String quantityRegex = "\\d+|\\.\\d+|\\d+\\.\\d+|\\d+\\s+\\d+/\\d+|\\d+/\\d+";
        Pattern pattern = Pattern.compile(quantityRegex);
        Matcher matcher = pattern.matcher(entry.getOriginalQuantity());
        if (!matcher.matches()){
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' has an invalid quantity.");
        }
        else if (entry.getOriginalQuantity().length() > 10){
            errors.add("Original ingredient '" + entry.getOriginalComponent() + "' cannot have a quantity exceeding 10 characters.");

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
        String quantityRegex = "\\d+|\\.\\d+|\\d+\\.\\d+|\\d+\\s+\\d+/\\d+|\\d+/\\d+";
        Pattern pattern = Pattern.compile(quantityRegex);
        Matcher matcher = pattern.matcher(entry.getSubstitutedQuantity());
        if (!matcher.matches()){
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' has an invalid quantity.");
        }
        else if (entry.getSubstitutedQuantity().length() > 10){
            errors.add("Substituted ingredient '" + entry.getSubstitutedComponent() + "' cannot have a quantity exceeding 10 characters.");

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
