package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebRecipe;
import com.thesis.recipease.model.web.recipe.WebTag;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class TagSanitizer implements Sanitizer{
    public WebRecipe sanitize(WebRecipe webRecipe){
        List<WebTag> holidays = webRecipe.getHolidays();
        if(holidays != null) {
            webRecipe.setHolidays(processTag(holidays));
            webRecipe.setHolidays(assignFields(holidays, "Holidays"));
        }

        List<WebTag> mealTypes = webRecipe.getMealTypes();
        if(mealTypes != null) {
            webRecipe.setMealTypes(processTag(mealTypes));
            webRecipe.setMealTypes(assignFields(mealTypes, "Meal Type"));
        }

        List<WebTag> cuisines = webRecipe.getCuisines();
        if(cuisines != null) {
            webRecipe.setCuisines(processTag(cuisines));
            webRecipe.setCuisines(assignFields(cuisines, "Cuisines"));
        }

        List<WebTag> allergens = webRecipe.getAllergens();
        if(allergens != null) {
            webRecipe.setAllergens(processTag(allergens));
            webRecipe.setAllergens(assignFields(allergens, "Allergens"));
        }

        List<WebTag> dietTypes = webRecipe.getDietTypes();
        if(dietTypes != null) {
            webRecipe.setDietTypes(processTag(dietTypes));
            webRecipe.setDietTypes(assignFields(dietTypes, "Diet Types"));
        }

        List<WebTag> cookingLevels = webRecipe.getCookingLevels();
        if(cookingLevels != null) {
            webRecipe.setCookingLevels(sortTags(cookingLevels));
            webRecipe.setCookingLevels(assignFields(cookingLevels, "Cooking Levels"));
        }

        List<WebTag> cookingStyles = webRecipe.getCookingStyles();
        if(cookingStyles != null) {
            webRecipe.setCookingStyles(processTag(cookingStyles));
            webRecipe.setCookingStyles(assignFields(cookingStyles,"Cooking Styles"));
        }
        return webRecipe;
    }

    private List<WebTag> assignFields(List<WebTag> listOfTags, String field){
        for(WebTag tag : listOfTags){
            tag.setField(field);
        }
        return listOfTags;
    }

    private List<WebTag> processTag(List<WebTag> listOfTags) {
        // Remove null or empty fields
        listOfTags.removeIf(tag -> tag.getValue() == null || tag.getValue().trim().isEmpty());

        // Sort alphabetically by field
        listOfTags.sort(Comparator.comparing(WebTag::getValue, String.CASE_INSENSITIVE_ORDER));

        List<WebTag> sortedTags = listOfTags.stream()
                .distinct()
                .toList();

        return sortedTags;
    }

    private static final List<String> SORT_ORDER = Arrays.asList(
            "Novice", "Beginner", "Intermediate", "Advanced", "Expert", "Chef-Level"
    );

    public static List<WebTag> sortTags(List<WebTag> listOfTags) {
        listOfTags.sort((tag1, tag2) -> {
            int index1 = SORT_ORDER.indexOf(tag1.getValue());
            int index2 = SORT_ORDER.indexOf(tag2.getValue());
            // Compare the indices to determine the order of sorting
            return Integer.compare(index1, index2);
        });
        return listOfTags;
    }

}
