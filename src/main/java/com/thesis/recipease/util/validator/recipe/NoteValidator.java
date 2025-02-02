package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebNote;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteValidator {
    public ArrayList<String> validateNote(WebNote webNote){
        ArrayList<String> errors = new ArrayList<String>();
        if(webNote != null){
            if(webNote.getNote().length() > 2500){
                errors.add("Note is too long. It should be 2500 characters in length at maximum.");
            }
        }
        return errors;
    }
}
