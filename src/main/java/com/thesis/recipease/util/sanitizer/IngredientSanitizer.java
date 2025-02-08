package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class IngredientSanitizer implements Sanitizer{
    public WebRecipe sanitize(WebRecipe webRecipe){
        List<WebIngredient> webIngredients = webRecipe.getIngredients();
        if(webIngredients != null) {
            Iterator<WebIngredient> ingredientIterator = webIngredients.iterator();
            WebIngredient webIngredient;
            while (ingredientIterator.hasNext()) {
                webIngredient = ingredientIterator.next();
                if (isIngredientNull(webIngredient)) {
                    ingredientIterator.remove();
                } else {
                    webIngredient.setComponent(webIngredient.getComponent().trim());
                    webIngredient.setQuantity(webIngredient.getQuantity().replaceAll("\\s*/\\s*", "/")
                            .replaceAll("\\s+", " ").trim());
                }
            }
        }
        webRecipe.setIngredients(webIngredients);
        return webRecipe;
    }

    private boolean isIngredientNull(WebIngredient webIngredient){
        return webIngredient.getComponent() == null &&
                webIngredient.getQuantity() == null &&
                webIngredient.getMeasurement() == null &&
                webIngredient.getPreparation() == null;
    }
}
