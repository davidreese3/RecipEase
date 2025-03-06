package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionValidator implements Validator<WebRecipe, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        List<WebDirection> webDirections = webRecipe.getDirections();
        if(webDirections == null){
            errors.add("Every recipe should have at least one direction.");
        }
        else {
            for (WebDirection webDirection : webDirections) {
                validateDirectionText(webDirection);
                validateMethod(webDirection);
                validateTemp(webDirection);
                validateHeatLevel(webDirection);
            }
        }
        return errors;
    }

    private void validateDirectionText(WebDirection webDirection){
        if(webDirection == null || webDirection.getDirection().isEmpty()){
            errors.add("Direction cannot be left empty.");
        }
        else if (webDirection.getDirection().length() > 300){
            errors.add("Direction cannot be longer than 300 characters.");
        }
    }

    private void validateMethod(WebDirection webDirection){
        if(!dropdownValidator.isValidMethod(webDirection.getMethod())){
            errors.add("Direction has an invalid method.");
        }
    }

    private void validateTemp(WebDirection webDirection){
        if(webDirection.getTemp() < 0){
            errors.add("Direction temperature cannot be less than 0.");
        }
    }

    private void validateHeatLevel(WebDirection webDirection){
        if(!dropdownValidator.isValidHeatLevel(webDirection.getLevel())){
            errors.add("Direction has an invalid heat level.");
        }
    }
}
