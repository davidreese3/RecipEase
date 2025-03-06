package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.util.WebSearch;
import org.springframework.stereotype.Service;

@Service
public class SearchSanitizer implements Sanitizer<WebSearch> {
    public WebSearch sanitize(WebSearch webSearch){
        webSearch.setName(webSearch.getName().trim());
        if(webSearch.getHolidays() == null) {
            webSearch.setHolidays("");
        }
        if(webSearch.getMealTypes() == null){
            webSearch.setMealTypes("");
        }
        if(webSearch.getCuisines() == null){
            webSearch.setCuisines("");
        }
        if(webSearch.getAllergens() == null){
            webSearch.setAllergens("");
        }
        if(webSearch.getDietTypes() == null){
            webSearch.setDietTypes("");
        }
        if(webSearch.getCookingLevels() == null){
            webSearch.setCookingLevels("");
        }
        if(webSearch.getCookingStyles() == null){
            webSearch.setCookingStyles("");
        }
            webSearch.setHolidays(webSearch.getHolidays().trim());
            webSearch.setMealTypes(webSearch.getMealTypes().trim());
            webSearch.setCuisines(webSearch.getCuisines().trim());
            webSearch.setAllergens(webSearch.getAllergens().trim());
            webSearch.setDietTypes(webSearch.getDietTypes().trim());
            webSearch.setCookingLevels(webSearch.getCookingLevels().trim());
            webSearch.setCookingStyles(webSearch.getCookingStyles().trim());

            return webSearch;

    }
}
