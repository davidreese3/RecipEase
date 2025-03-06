package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebNote;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteValidator implements Validator<WebRecipe, ArrayList<String>> {
    public ArrayList<String> validate(WebRecipe webRecipe){
        ArrayList<String> errors = new ArrayList<String>();
        WebNote webNote = webRecipe.getNote();
        if(webNote != null){
            if(webNote.getNote().length() > 2500){
                errors.add("Note is too long. It should be 2500 characters in length at maximum.");
            }
        }
        return errors;
    }
}
