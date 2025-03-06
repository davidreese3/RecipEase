package com.thesis.recipease.model.web.recipe;

import com.thesis.recipease.model.web.recipe.util.WebVariation;

import java.util.List;

public class WebRecipe {
    private WebInfo info;
    private List<WebIngredient> ingredients;
    private List<WebDirection> directions;
    private WebNote note;
    private List<WebLink> links;
    private List<WebUserSubstitutionEntry> userSubstitutionEntries;
    private WebPhoto photo;
    private List<WebTag> holidays;
    private List<WebTag> mealTypes;
    private List<WebTag> cuisines;
    private List<WebTag> allergens;
    private List<WebTag> dietTypes;
    private List<WebTag> cookingLevels;
    private List<WebTag> cookingStyles;
    private WebVariation variation;

    public WebRecipe(){}

    public WebRecipe(WebInfo info, List<WebIngredient> ingredients, List<WebDirection> directions, WebNote note, List<WebLink> links, List<WebUserSubstitutionEntry> userSubstitutionEntries, List<WebTag> holidays, List<WebTag> mealTypes, List<WebTag> cuisines, List<WebTag> allergens, List<WebTag> dietTypes, List<WebTag> cookingLevels, List<WebTag> cookingStyles, WebVariation variation) {
        this.info = info;
        this.ingredients = ingredients;
        this.directions = directions;
        this.note = note;
        this.links = links;
        this.userSubstitutionEntries = userSubstitutionEntries;
        this.holidays = holidays;
        this.mealTypes = mealTypes;
        this.cuisines = cuisines;
        this.allergens = allergens;
        this.dietTypes = dietTypes;
        this.cookingLevels = cookingLevels;
        this.cookingStyles = cookingStyles;
        this.variation = variation;
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

    public List<WebLink> getLinks() {
        return links;
    }

    public void setLinks(List<WebLink> links) {
        this.links = links;
    }

    public List<WebUserSubstitutionEntry> getUserSubstitutionEntries() {
        return userSubstitutionEntries;
    }

    public void setUserSubstitutionEntries(List<WebUserSubstitutionEntry> userSubstitutionEntries) {
        this.userSubstitutionEntries = userSubstitutionEntries;
    }

    public WebPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(WebPhoto photo) {
        this.photo = photo;
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

    public WebVariation getVariation() {
        return variation;
    }

    public void setVariation(WebVariation variation) {
        this.variation = variation;
    }
}