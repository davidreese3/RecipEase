package com.thesis.recipease.util.sanitizer.recipe;

import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.util.sanitizer.Sanitizer;
import com.thesis.recipease.util.sanitizer.recipe.util.QuantitySanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class IngredientSanitizer implements Sanitizer<WebRecipe> {
    @Autowired
    QuantitySanitizer quantitySanitizer;

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
                    webIngredient.setQuantity(quantitySanitizer.sanitize(webIngredient.getQuantity()));
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
