package com.thesis.recipease.util.validator;

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
        for(WebDirection webDirection: webDirections){
            checkLengthOfDirection(webDirection.getDirection());
            checkMethodOfDirection(webDirection.getMethod());
            checkTempOfDirection(webDirection.getTemp());
            checkHeatLevelOfDirection(webDirection.getLevel());
        }
        //Direction
        //Method valid
        //Temp above 0
        //heat valid
        return errors;
    }

    private void checkLengthOfDirection(String direction){
        if(direction == null || direction.isEmpty()){
            errors.add("Direction cannot be left empty.");
        }
        else if (direction.length() > 300){
            errors.add("Direction cannot be longer than 300 characters.");
        }
    }

    private void checkMethodOfDirection(String method){
        if(!dropdownValidator.isValidMethod(method)){
            errors.add("Direction has an invalid method.");
        }
    }

    private void checkTempOfDirection(int temp){
        if(temp < 0){
            errors.add("Direction temperature cannot be less than 0.");
        }
    }

    private void checkHeatLevelOfDirection(String heatLevel){
        if(!dropdownValidator.isValidHeatLevel(heatLevel)){
            errors.add("Direction has an invalid heat level.");
        }
    }
}
