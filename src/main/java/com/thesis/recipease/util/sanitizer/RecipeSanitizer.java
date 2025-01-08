package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeSanitizer {
    public WebRecipe sanitizeRecipe(WebRecipe webRecipe) {
        // == required fields
        //info
        WebInfo webInfo = webRecipe.getInfo();
        if (webInfo.getPrepMin() == null) webInfo.setPrepMin(0);
        if (webInfo.getPrepHr() == null) webInfo.setPrepHr(0);
        if (webInfo.getProcessMin() == null) webInfo.setProcessMin(0);
        if (webInfo.getProcessHr() == null) webInfo.setProcessHr(0);
        if (webInfo.getYield() == null) webInfo.setYield(1.0);
        webInfo.setTotalMin((webInfo.getPrepMin() + webInfo.getProcessMin()) % 60);
        webInfo.setTotalHr(webInfo.getPrepHr() + webInfo.getProcessHr() + ((webInfo.getPrepMin() + webInfo.getProcessMin()) / 60));
        webRecipe.setInfo(webInfo);

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

        //SANITIZE LINKS

        List<WebLink> webLinks = webRecipe.getLinks();
        Iterator<WebLink> linkIterator = webLinks.iterator();
        WebLink webLink;
        while(linkIterator.hasNext()){
            webLink = linkIterator.next();
            if(webLink.getLink() == null || webLink.getLink().trim().equals("")){
                linkIterator.remove();
            }
        }
        webRecipe.setLinks(webLinks);

        //tags
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

        return sortedTags;
    }

    public static WebRecipe setTags(WebRecipe webRecipe){
        List<WebTag> holidays = webRecipe.getHolidays();
        webRecipe.setHolidays(processTag(holidays));

        List<WebTag> mealTypes = webRecipe.getMealTypes();
        webRecipe.setMealTypes(processTag(mealTypes));

        List<WebTag> cuisines = webRecipe.getCuisines();
        webRecipe.setCuisines(processTag(cuisines));

        List<WebTag> allergens = webRecipe.getAllergens();
        webRecipe.setAllergens(processTag(allergens));

        List<WebTag> dietType = webRecipe.getDietTypes();
        webRecipe.setDietTypes(processTag(dietType));

        List<WebTag> cookingLevels = webRecipe.getCookingLevels();
        webRecipe.setCookingLevels(processTag(cookingLevels));

        List<WebTag> cookingStyles = webRecipe.getCookingStyles();
        webRecipe.setCookingStyles(processTag(cookingStyles));
        return webRecipe;
    }

}
