package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebNote;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

@Service
public class NoteSanitizer implements Sanitizer<WebRecipe>{
    public WebRecipe sanitize(WebRecipe webRecipe){
        WebNote webNote = webRecipe.getNote();
        webNote.setNote(webNote.getNote().trim());
        webRecipe.setNote(webNote);
        return webRecipe;
    }
}
