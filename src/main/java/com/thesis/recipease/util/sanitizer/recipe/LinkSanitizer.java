package com.thesis.recipease.util.sanitizer.recipe;

import com.thesis.recipease.model.web.recipe.WebLink;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class LinkSanitizer implements Sanitizer<WebRecipe> {
    public WebRecipe sanitize(WebRecipe webRecipe) {
        List<WebLink> webLinks = webRecipe.getLinks();
        if (webLinks != null) {
            Set<String> uniqueLinks = new HashSet<>();
            Iterator<WebLink> linkIterator = webLinks.iterator();
            while (linkIterator.hasNext()) {
                WebLink webLink = linkIterator.next();
                String linkValue = webLink.getLink();
                if (linkValue == null || linkValue.trim().isEmpty() || !uniqueLinks.add(linkValue)) {
                    linkIterator.remove();
                }
            }
        }
        webRecipe.setLinks(webLinks);
        return webRecipe;
    }
}
