package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class DirectionSanitizer implements Sanitizer<WebRecipe>{
    public WebRecipe sanitize(WebRecipe webRecipe) {
        List<WebDirection> webDirections = webRecipe.getDirections();
        if (webDirections != null) {
            Iterator<WebDirection> directionIterator = webDirections.iterator();
            WebDirection webDirection;
            while (directionIterator.hasNext()) {
                webDirection = directionIterator.next();
                if (isDirectionNull(webDirection)) {
                    directionIterator.remove();
                } else {
                    webDirection.setDirection(webDirection.getDirection().trim());
                    if (webDirection.getTemp() == null) {
                        webDirection.setTemp(0);
                    }
                }
            }
        }
        webRecipe.setDirections(webDirections);
        return webRecipe;
    }

    private boolean isDirectionNull(WebDirection webDirection){
        return webDirection.getDirection() == null &&
                webDirection.getMethod() == null &&
                webDirection.getTemp() == null &&
                webDirection.getLevel() == null;
    }
}

