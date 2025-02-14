package com.thesis.recipease.model.web.recipe.util;

import com.thesis.recipease.model.web.recipe.WebTag;

public class WebSearch {
    private String name;
    private String holidays;
    private String mealTypes;
    private String cuisines;
    private String allergens;
    private String dietTypes;
    private String cookingLevels;
    private String cookingStyles;

    public WebSearch() {}

    public WebSearch(String name, String holidays, String mealTypes, String cuisines, String allergens, String dietTypes, String cookingLevels, String cookingStyles) {
        this.name = name;
        this.holidays = holidays;
        this.mealTypes = mealTypes;
        this.cuisines = cuisines;
        this.allergens = allergens;
        this.dietTypes = dietTypes;
        this.cookingLevels = cookingLevels;
        this.cookingStyles = cookingStyles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
    }

    public String getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(String mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(String dietTypes) {
        this.dietTypes = dietTypes;
    }

    public String getCookingLevels() {
        return cookingLevels;
    }

    public void setCookingLevels(String cookingLevels) {
        this.cookingLevels = cookingLevels;
    }

    public String getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(String cookingStyles) {
        this.cookingStyles = cookingStyles;
    }

    public boolean areTagsAllEmpty() {
        return holidays.isBlank() && mealTypes.isBlank() && cuisines.isBlank() &&
                allergens.isBlank() && dietTypes.isBlank() && cookingLevels.isBlank() &&
                cookingStyles.isBlank();
    }

}
