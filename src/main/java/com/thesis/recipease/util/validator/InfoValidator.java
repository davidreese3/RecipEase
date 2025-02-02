package com.thesis.recipease.util.validator;

import com.thesis.recipease.model.web.recipe.WebInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoValidator {
    @Autowired
    private DropdownValidator dropdownValidator;
    private ArrayList<String> errors;

    public ArrayList<String> validateInfo(WebInfo webInfo){
        errors = new ArrayList<String>();
        checkLengthOfName(webInfo.getName());
        checkLengthOfDescription(webInfo.getDescription());
        checkQuantityOfYield(webInfo.getYield());
        CheckUnitOfYield(webInfo.getUnitOfYield());
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
}
