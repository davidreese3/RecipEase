package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebIngredient;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.tag.WebTag;

import java.util.*;

public class RecipeSanitizer {

    public static WebRecipe sanitizeRecipe(WebRecipe webRecipe) {
        // == required fields
        //info
        if (webRecipe.getPrepMin() == null) webRecipe.setPrepMin(0);
        if (webRecipe.getPrepHr() == null) webRecipe.setPrepHr(0);
        if (webRecipe.getProcessMin() == null) webRecipe.setProcessMin(0);
        if (webRecipe.getProcessHr() == null) webRecipe.setProcessHr(0);
        if (webRecipe.getYield() == null) webRecipe.setYield(1.0);
        webRecipe.setTotalMin((webRecipe.getPrepMin() + webRecipe.getProcessMin()) % 60);
        webRecipe.setTotalHr(webRecipe.getPrepHr() + webRecipe.getProcessHr() + ((webRecipe.getPrepMin() + webRecipe.getProcessMin()) / 60));

        //ingredients
        List<WebIngredient> webIngredients = webRecipe.getIngredients();
        Iterator<WebIngredient> ingredientIterator = webIngredients.iterator();
        WebIngredient webIngredient;
        while(ingredientIterator.hasNext()) {
            webIngredient = ingredientIterator.next();
            System.out.println("[[" + webIngredient.getWholeNumberQuantity() + "||" + webIngredient.getFractionQuantity() + "]]");

            if (isIngredientNull(webIngredient)) {
                ingredientIterator.remove();
                break;
            }
            if(webIngredient.getWholeNumberQuantity() == null) {
                webIngredient.setWholeNumberQuantity(0);
            }
            if(isIngredientZero(webIngredient)){
                ingredientIterator.remove();
            }
        }
        webRecipe.setIngredients(webIngredients);
        //directions

        List<WebDirection> webDirections = webRecipe.getDirections();
        Iterator<WebDirection> directionIterator = webDirections.iterator();
        WebDirection webDirection;
        while(directionIterator.hasNext()){
            webDirection = directionIterator.next();
            if(isDirectionNull(webDirection)){
                directionIterator.remove();
            }
            else if(webDirection.getTemp() == null){
                webDirection.setTemp(0);
            }
        }
        webRecipe.setDirections(webDirections);

        //process Tags
        webRecipe = setTags(webRecipe);

        return webRecipe;
    }

    public static boolean isIngredientNull(WebIngredient webIngredient){
        return webIngredient.getComponent() == null &&
                webIngredient.getWholeNumberQuantity() == null &&
                webIngredient.getFractionQuantity() == null &&
                webIngredient.getMeasurement() == null &&
                webIngredient.getPreparation() == null;
    }

    public static boolean isIngredientZero(WebIngredient webIngredient){
        return webIngredient.getWholeNumberQuantity() == 0 && webIngredient.getFractionQuantity().equals("0");
    }

    public static boolean isDirectionNull(WebDirection webDirection){
        return webDirection.getDirection() == null &&
                webDirection.getMethod() == null &&
                webDirection.getTemp() == null &&
                webDirection.getLevel() == null;
    }

    public static List<WebTag> processTag(List<WebTag> listOfTags) {
        // Remove null or empty fields
        listOfTags.removeIf(tag -> tag.getField() == null || tag.getField().trim().isEmpty());

        // Sort alphabetically by field
        listOfTags.sort(Comparator.comparing(WebTag::getField, String.CASE_INSENSITIVE_ORDER));

        List<WebTag> sortedTags = listOfTags.stream()
                .distinct()
                .toList();

        return listOfTags;
    }

    public static WebRecipe setTags(WebRecipe webRecipe){
        List<WebTag> holidays = webRecipe.getHolidays();
        processTag(holidays);
        webRecipe.setHolidays(holidays);

        List<WebTag> mealTypes = webRecipe.getMealTypes();
        processTag(mealTypes);
        webRecipe.setHolidays(mealTypes);

        List<WebTag> cuisines = webRecipe.getCuisines();
        processTag(cuisines);
        webRecipe.setHolidays(cuisines);

        List<WebTag> allergens = webRecipe.getAllergens();
        processTag(allergens);
        webRecipe.setHolidays(allergens);

        List<WebTag> dietType = webRecipe.getDietTypes();
        processTag(dietType);
        webRecipe.setHolidays(dietType);

        List<WebTag> cookingLevels = webRecipe.getCookingLevels();
        processTag(cookingLevels);
        webRecipe.setHolidays(cookingLevels);

        return webRecipe;
    }

}
