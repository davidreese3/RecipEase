package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebLink;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkValidator implements Validator{
    public ArrayList<String> validate(WebRecipe webRecipe){
        ArrayList<String> errors = new ArrayList<String>();
        List<WebLink> webLinks = webRecipe.getLinks();
        if(webLinks != null) {
            for (WebLink webLink : webLinks){
                if (webLink.getLink().length() > 125) {
                    errors.add("Link '" + webLink.getLink() + "' is too long. It should be 125 characters in length at maximum.");
                }
            }
        }
        return errors;
    }
}
