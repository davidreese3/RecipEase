package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.recipe.RecipeNote;
import com.thesis.recipease.model.web.recipe.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeSanitizer {
    WebRecipe webRecipe;
    public WebRecipe sanitizeRecipe(WebRecipe webRecipe) {
        this.webRecipe = webRecipe;
        sanitizeInfo();
        sanitizeIngredients();
        sanitizeDirections();
        sanitizeNote();
        sanitizeLinks();
        sanitizeUserSubs();
        sanitizeTags();
        return webRecipe;
    }

    public void sanitizeInfo(){
        WebInfo webInfo = webRecipe.getInfo();
        if (webInfo.getPrepMin() == null) webInfo.setPrepMin(0);
        if (webInfo.getPrepHr() == null) webInfo.setPrepHr(0);
        if (webInfo.getProcessMin() == null) webInfo.setProcessMin(0);
        if (webInfo.getProcessHr() == null) webInfo.setProcessHr(0);
        if (webInfo.getYield() == null) webInfo.setYield(1.0);
        webInfo.setTotalMin((webInfo.getPrepMin() + webInfo.getProcessMin()) % 60);
        webInfo.setTotalHr(webInfo.getPrepHr() + webInfo.getProcessHr() + ((webInfo.getPrepMin() + webInfo.getProcessMin()) / 60));
        webRecipe.setInfo(webInfo);
    }

    public void sanitizeIngredients(){
        List<WebIngredient> webIngredients = webRecipe.getIngredients();
        Iterator<WebIngredient> ingredientIterator = webIngredients.iterator();
        WebIngredient webIngredient;
        while(ingredientIterator.hasNext()) {
            webIngredient = ingredientIterator.next();
            System.out.println("[[" + webIngredient.getWholeNumberQuantity() + "||" + webIngredient.getFractionQuantity() + "]]");

            if (isIngredientNull(webIngredient)) {
                ingredientIterator.remove();
            }
            else {
                if (webIngredient.getWholeNumberQuantity() == null) {
                    webIngredient.setWholeNumberQuantity(0);
                }
                if (isIngredientZero(webIngredient)) {
                    ingredientIterator.remove();
                }
            }
        }
        webRecipe.setIngredients(webIngredients);
    }

    public void sanitizeDirections(){
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
    }

    public void sanitizeNote(){
        WebNote webNote = webRecipe.getNote();
        if(webNote.getNote().trim().equals("")){
            webNote.setNote("");
        }
    }

    public void sanitizeLinks(){
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
    }

    public void sanitizeUserSubs(){
        List<WebUserSubstitutionEntry> webUserSubstitutionEntries = webRecipe.getUserSubstitutionEntries();
        if(webUserSubstitutionEntries != null) {
            Iterator<WebUserSubstitutionEntry> userSubstitutionEntryIterator = webUserSubstitutionEntries.iterator();
            WebUserSubstitutionEntry webUserSubstitutionEntry;
            while (userSubstitutionEntryIterator.hasNext()) {
                System.out.println("??? INSIDE WHILE LOOP");
                webUserSubstitutionEntry = userSubstitutionEntryIterator.next();
                System.out.println("||TO STRING: "+ webUserSubstitutionEntry.toString());
                System.out.println("{{ " + webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity());
                if (isUserSubNull(webUserSubstitutionEntry)) {
                    System.out.println("WHOLE THING NULL");
                    userSubstitutionEntryIterator.remove();
                }
                else {
                    if (webUserSubstitutionEntry.getOriginalWholeNumberQuantity() == null) {
                        webUserSubstitutionEntry.setOriginalWholeNumberQuantity(0);
                    }
                    if (webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity() == null) {
                        System.out.println("SUB WHOLE IS NULL {{}}");
                        webUserSubstitutionEntry.setSubstitutedWholeNumberQuantity(0);
                    } else {
                        System.out.println("SUB WHOLE IS NOT NULL {{}}" + webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity());

                    }
                    if (isUserSubZero(webUserSubstitutionEntry)) {
                        userSubstitutionEntryIterator.remove();
                    }
                }
            }
        }
        webRecipe.setUserSubstitutionEntries(webUserSubstitutionEntries);
    }

    public void sanitizeTags(){
        List<WebTag> holidays = webRecipe.getHolidays();
        if(holidays != null) {
            webRecipe.setHolidays(processTag(holidays));
        }

        List<WebTag> mealTypes = webRecipe.getMealTypes();
        if(mealTypes != null) {
            webRecipe.setMealTypes(processTag(mealTypes));
        }

        List<WebTag> cuisines = webRecipe.getCuisines();
        if(cuisines != null) {
            webRecipe.setCuisines(processTag(cuisines));
        }

        List<WebTag> allergens = webRecipe.getAllergens();
        if(allergens != null) {
            webRecipe.setAllergens(processTag(allergens));
        }

        List<WebTag> dietTypes = webRecipe.getDietTypes();
        if(dietTypes != null) {
            webRecipe.setDietTypes(processTag(dietTypes));
        }

        List<WebTag> cookingLevels = webRecipe.getCookingLevels();
        if(cookingLevels != null) {
            webRecipe.setCookingLevels(processTag(cookingLevels));
        }

        List<WebTag> cookingStyles = webRecipe.getCookingStyles();
        if(cookingStyles != null) {
            webRecipe.setCookingStyles(processTag(cookingStyles));
        }
    }

    private boolean isIngredientNull(WebIngredient webIngredient){
        return webIngredient.getComponent() == null &&
                webIngredient.getWholeNumberQuantity() == null &&
                webIngredient.getFractionQuantity() == null &&
                webIngredient.getMeasurement() == null &&
                webIngredient.getPreparation() == null;
    }

    private boolean isIngredientZero(WebIngredient webIngredient){
        return webIngredient.getWholeNumberQuantity() == 0 && webIngredient.getFractionQuantity().equals("0");
    }

    private boolean isDirectionNull(WebDirection webDirection){
        return webDirection.getDirection() == null &&
                webDirection.getMethod() == null &&
                webDirection.getTemp() == null &&
                webDirection.getLevel() == null;
    }

    private boolean isUserSubNull(WebUserSubstitutionEntry webUserSubstitutionEntry){
        return webUserSubstitutionEntry.getOriginalComponent() == null &&
                webUserSubstitutionEntry.getOriginalWholeNumberQuantity() == null &&
                webUserSubstitutionEntry.getOriginalFractionQuantity() == null &&
                webUserSubstitutionEntry.getOriginalMeasurement() == null &&
                webUserSubstitutionEntry.getOriginalPreparation() == null &&
                webUserSubstitutionEntry.getSubstitutedComponent() == null &&
                webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity() == null &&
                webUserSubstitutionEntry.getSubstitutedFractionQuantity() == null &&
                webUserSubstitutionEntry.getSubstitutedMeasurement() == null &&
                webUserSubstitutionEntry.getSubstitutedPreparation() == null;
    }

    private boolean isUserSubZero(WebUserSubstitutionEntry webUserSubstitutionEntry){
        return webUserSubstitutionEntry.getOriginalWholeNumberQuantity() == 0 &&
                webUserSubstitutionEntry.getOriginalFractionQuantity().equals("0") &&
                webUserSubstitutionEntry.getSubstitutedWholeNumberQuantity() == 0 &&
                webUserSubstitutionEntry.getSubstitutedFractionQuantity().equals("0");
    }

    private List<WebTag> processTag(List<WebTag> listOfTags) {
        // Remove null or empty fields
        listOfTags.removeIf(tag -> tag.getField() == null || tag.getField().trim().isEmpty());

        // Sort alphabetically by field
        listOfTags.sort(Comparator.comparing(WebTag::getField, String.CASE_INSENSITIVE_ORDER));

        List<WebTag> sortedTags = listOfTags.stream()
                .distinct()
                .toList();

        return sortedTags;
    }
}
