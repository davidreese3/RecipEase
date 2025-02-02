package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionValidator {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validateDirections(List<WebDirection> webDirections){
        errors = new ArrayList<String>();
        if(webDirections == null){
            errors.add("Every recipe should have at least one direction.");
        }
        else {
            for (WebDirection webDirection : webDirections) {
                validateLengthOfDirection(webDirection.getDirection());
                validateMethodOfDirection(webDirection.getMethod());
                validateTempOfDirection(webDirection.getTemp());
                validateHeatLevelOfDirection(webDirection.getLevel());
            }
        }
        return errors;
    }

    private void validateLengthOfDirection(String direction){
        if(direction == null || direction.isEmpty()){
            errors.add("Direction cannot be left empty.");
        }
        else if (direction.length() > 300){
            errors.add("Direction cannot be longer than 300 characters.");
        }
    }

    private void validateMethodOfDirection(String method){
        if(!dropdownValidator.isValidMethod(method)){
            errors.add("Direction has an invalid method.");
        }
    }

    private void validateTempOfDirection(int temp){
        if(temp < 0){
            errors.add("Direction temperature cannot be less than 0.");
        }
    }

    private void validateHeatLevelOfDirection(String heatLevel){
        if(!dropdownValidator.isValidHeatLevel(heatLevel)){
            errors.add("Direction has an invalid heat level.");
        }
    }
}
