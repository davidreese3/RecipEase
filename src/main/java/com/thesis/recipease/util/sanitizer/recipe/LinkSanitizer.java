package com.thesis.recipease.util.sanitizer.recipe;

import com.thesis.recipease.model.web.recipe.WebLink;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class LinkSanitizer implements Sanitizer<WebRecipe> {
    public WebRecipe sanitize(WebRecipe webRecipe){
        List<WebLink> webLinks = webRecipe.getLinks();
        if (webLinks != null){
            Iterator<WebLink> linkIterator = webLinks.iterator();
            WebLink webLink;
            while(linkIterator.hasNext()){
                webLink = linkIterator.next();
                if(webLink.getLink() == null || webLink.getLink().trim().equals("")){
                    linkIterator.remove();
                }
            }
        }
        webRecipe.setLinks(webLinks);
        return webRecipe;
    }
}
