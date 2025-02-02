package com.thesis.recipease.util.validator.recipe;

import com.thesis.recipease.model.web.recipe.WebLink;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkValidator {
    public ArrayList<String> validateLink(List<WebLink> webLinks){
        ArrayList<String> errors = new ArrayList<String>();
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
