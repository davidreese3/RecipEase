package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebInfo;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InfoValidator implements Validator<WebRecipe, ArrayList<String>> {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validate(WebRecipe webRecipe){
        errors = new ArrayList<String>();
        WebInfo webInfo = webRecipe.getInfo();
        checkLengthOfName(webInfo.getName());
        checkLengthOfDescription(webInfo.getDescription());
        checkQuantityOfYield(webInfo.getYield());
        CheckUnitOfYield(webInfo.getUnitOfYield());
        CheckMinutes(webInfo.getPrepMin(), webInfo.getProcessMin());
        CheckHours(webInfo.getPrepHr(), webInfo.getProcessHr());
        return errors;
    }

    private void checkLengthOfName(String name) {
        if (name == null || name.trim().isEmpty()) {
            errors.add("Recipe name is required.");
        }
        else if (name.length() > 40) {
            errors.add("Recipe name cannot exceed 40 characters.");
        }
    }

    private void checkLengthOfDescription(String description) {
        if (description.length() > 1000){
            errors.add("Recipe description cannot exceed 1000 characters.");
        }
    }

    private void checkQuantityOfYield(Double yield) {
        if (yield == null){
            errors.add("Recipe yield is required.");
        }
        else if (yield <= 0){
            errors.add("Recipe must make more than than 0.");
        }
    }

    private void CheckUnitOfYield(String unitOfYield) {
        if(unitOfYield == null) {
            errors.add("Unit of yield is required.");
        }
        else if (!dropdownValidator.isValidUnitOfYield(unitOfYield)){
            errors.add("Unit of yield has an invalid unit.");
        }
    }

    private void CheckMinutes(int prepMin, int processMin){
        if(prepMin < 0 || prepMin > 59){
            errors.add("Preparation minutes must be within 0-59.");
        }
        if(processMin < 0 || processMin > 59){
            errors.add("Process minutes must be within 0-59.");
        }
    }

    private void CheckHours(int prepHr, int processHr){
        if(prepHr < 0){
            errors.add("Process hours must be greater than 0.");
        }
        if(processHr < 0){
            errors.add("Process hours must be greater than 0.");
        }
    }
}
