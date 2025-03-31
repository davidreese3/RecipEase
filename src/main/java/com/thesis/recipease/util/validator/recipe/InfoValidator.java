package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebInfo;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import com.thesis.recipease.util.validator.util.DropdownValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InfoValidator implements Validator<WebRecipe, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        WebInfo webInfo = webRecipe.getInfo();
        validateName(webInfo.getName());
        validateDescription(webInfo.getDescription());
        validateQuantity(webInfo.getYield());
        validateUnitOfYield(webInfo.getUnitOfYield());
        validateMinutes(webInfo.getPrepMin(), webInfo.getProcessMin());
        validateHours(webInfo.getPrepHr(), webInfo.getProcessHr());
        return errors;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            errors.add("Recipe name is required.");
        }
        else if (name.length() > 40) {
            errors.add("Recipe name cannot exceed 40 characters.");
        }
    }

    private void validateDescription(String description) {
        if (description.length() > 1000){
            errors.add("Recipe description cannot exceed 1000 characters.");
        }
    }

    private void validateQuantity(String quantity) {
        String quantityRegex = "\\d+|\\.\\d+|\\d+\\.\\d+|\\d+\\s+\\d+/\\d+|\\d+/\\d+";
        Pattern pattern = Pattern.compile(quantityRegex);
        Matcher matcher = pattern.matcher(quantity);
        if (!matcher.matches()){
            errors.add("Recipe yield has an invalid quantity.");
        }
        else if (quantity.length() > 10){
            errors.add("Recipe yield must be more than 0.");

        }
    }

    private void validateUnitOfYield(String unitOfYield) {
        if(unitOfYield == null) {
            errors.add("Unit of yield is required.");
        }
        else if (!dropdownValidator.isValidUnitOfYield(unitOfYield)){
            errors.add("Unit of yield has an invalid unit.");
        }
    }

    private void validateMinutes(int prepMin, int processMin){
        if(prepMin < 0 || prepMin > 59){
            errors.add("Preparation minutes must be within 0-59.");
        }
        if(processMin < 0 || processMin > 59){
            errors.add("Process minutes must be within 0-59.");
        }
    }

    private void validateHours(int prepHr, int processHr){
        if(prepHr < 0){
            errors.add("Process hours must be greater than 0.");
        }
        if(processHr < 0){
            errors.add("Process hours must be greater than 0.");
        }
    }
}
