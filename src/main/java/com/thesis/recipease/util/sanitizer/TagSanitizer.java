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
            webRecipe.setCookingLevels(sortTags(cookingLevels));
        }

        List<WebTag> cookingStyles = webRecipe.getCookingStyles();
        if(cookingStyles != null) {
            webRecipe.setCookingStyles(processTag(cookingStyles));
        }
        return webRecipe;
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

    private static final List<String> SORT_ORDER = Arrays.asList(
            "Novice", "Beginner", "Intermediate", "Advanced", "Expert", "Chef-Level"
    );

    public static List<WebTag> sortTags(List<WebTag> listOfTags) {
        listOfTags.sort((tag1, tag2) -> {
            int index1 = SORT_ORDER.indexOf(tag1.getField());
            int index2 = SORT_ORDER.indexOf(tag2.getField());
            // Compare the indices to determine the order of sorting
            return Integer.compare(index1, index2);
        });
        return listOfTags;
    }

}
