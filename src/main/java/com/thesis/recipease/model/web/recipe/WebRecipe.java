package com.thesis.recipease.model.web.recipe;

import com.thesis.recipease.model.web.recipe.tag.*;

import java.util.List;

public class WebRecipe {
    private WebInfo info;
    private List<WebIngredient> ingredients;
    private List<WebDirection> directions;
    private WebNote note;
    private List<WebTag> holidays;
    private List<WebTag> mealTypes;
    private List<WebTag> cuisines;
    private List<WebTag> allergens;
    private List<WebTag> dietTypes;
    private List<WebTag> cookingLevels;
    private List<WebTag> cookingStyles;

    public WebRecipe(){}

    public WebRecipe(WebInfo info, List<WebIngredient> ingredients, List<WebDirection> directions, WebNote note, List<WebTag> holidays, List<WebTag> mealTypes, List<WebTag> cuisines, List<WebTag> allergens, List<WebTag> dietTypes, List<WebTag> cookingLevels, List<WebTag> cookingStyles) {
        this.info = info;
        this.ingredients = ingredients;
        this.directions = directions;
        this.note = note;
        this.holidays = holidays;
        this.mealTypes = mealTypes;
        this.cuisines = cuisines;
        this.allergens = allergens;
        this.dietTypes = dietTypes;
        this.cookingLevels = cookingLevels;
        this.cookingStyles = cookingStyles;
    }

    public WebInfo getInfo() {
        return info;
    }

    public void setInfo(WebInfo info) {
        this.info = info;
    }

    public List<WebIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<WebIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<WebDirection> getDirections() {
        return directions;
    }

    public void setDirections(List<WebDirection> directions) {
        this.directions = directions;
    }

    public WebNote getNote() {
        return note;
    }

    public void setNote(WebNote note) {
        this.note = note;
    }

    public List<WebTag> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<WebTag> holidays) {
        this.holidays = holidays;
    }

    public List<WebTag> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<WebTag> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public List<WebTag> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<WebTag> cuisines) {
        this.cuisines = cuisines;
    }

    public List<WebTag> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<WebTag> allergens) {
        this.allergens = allergens;
    }

    public List<WebTag> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(List<WebTag> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public List<WebTag> getCookingLevels() {
        return cookingLevels;
    }

    public void setCookingLevels(List<WebTag> cookingLevels) {
        this.cookingLevels = cookingLevels;
    }

    public List<WebTag> getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(List<WebTag> cookingStyles) {
        this.cookingStyles = cookingStyles;
    }
}
