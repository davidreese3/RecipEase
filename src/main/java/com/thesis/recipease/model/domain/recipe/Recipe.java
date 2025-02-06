package com.thesis.recipease.model.domain.recipe;

import com.thesis.recipease.model.web.recipe.WebTag;

import java.util.LinkedHashMap;
import java.util.List;

public class Recipe {
    private RecipeInfo info;
    private List<RecipeIngredient> ingredients;
    private List<RecipeDirection> directions;
    private RecipeNote note;
    private List<RecipeLink> links;
    private List<RecipeUserSubstitutionEntry> userSubstitutionEntries;
    private RecipePhoto photo;
    private List<RecipeTag> holidays;
    private List<RecipeTag> mealTypes;
    private List<RecipeTag> cuisines;
    private List<RecipeTag> allergens;
    private List<RecipeTag> dietTypes;
    private List<RecipeTag> cookingLevels;
    private List<RecipeTag> cookingStyles;
    private List<RecipeComment> comments;


    public Recipe(){}

    public Recipe(RecipeInfo info, List<RecipeIngredient> ingredients, List<RecipeDirection> directions, RecipeNote note, List<RecipeLink> links, List<RecipeUserSubstitutionEntry> userSubstitutionEntries, RecipePhoto photo, List<RecipeTag> holidays, List<RecipeTag> mealTypes, List<RecipeTag> cuisines, List<RecipeTag> allergens, List<RecipeTag> dietTypes, List<RecipeTag> cookingLevels, List<RecipeTag> cookingStyles, List<RecipeComment> comments) {
        this.info = info;
        this.ingredients = ingredients;
        this.directions = directions;
        this.note = note;
        this.links = links;
        this.userSubstitutionEntries = userSubstitutionEntries;
        this.photo = photo;
        this.holidays = holidays;
        this.mealTypes = mealTypes;
        this.cuisines = cuisines;
        this.allergens = allergens;
        this.dietTypes = dietTypes;
        this.cookingLevels = cookingLevels;
        this.cookingStyles = cookingStyles;
        this.comments = comments;
    }

    public RecipeInfo getInfo() {
        return info;
    }

    public void setInfo(RecipeInfo info) {
        this.info = info;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeDirection> getDirections() {
        return directions;
    }

    public void setDirections(List<RecipeDirection> directions) {
        this.directions = directions;
    }

    public RecipeNote getNote() {
        return note;
    }

    public void setNote(RecipeNote note) {
        this.note = note;
    }

    public List<RecipeLink> getLinks() {
        return links;
    }

    public void setLinks(List<RecipeLink> links) {
        this.links = links;
    }

    public List<RecipeUserSubstitutionEntry> getUserSubstitutionEntries() {
        return userSubstitutionEntries;
    }

    public void setUserSubstitutionEntries(List<RecipeUserSubstitutionEntry> userSubstitutionEntries) {
        this.userSubstitutionEntries = userSubstitutionEntries;
    }

    public RecipePhoto getPhoto() {
        return photo;
    }

    public void setPhoto(RecipePhoto photo) {
        this.photo = photo;
    }

    public List<RecipeTag> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<RecipeTag> holidays) {
        this.holidays = holidays;
    }

    public List<RecipeTag> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<RecipeTag> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public List<RecipeTag> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<RecipeTag> cuisines) {
        this.cuisines = cuisines;
    }

    public List<RecipeTag> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<RecipeTag> allergens) {
        this.allergens = allergens;
    }

    public List<RecipeTag> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(List<RecipeTag> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public List<RecipeTag> getCookingLevels() {
        return cookingLevels;
    }

    public void setCookingLevels(List<RecipeTag> cookingLevels) {
        this.cookingLevels = cookingLevels;
    }

    public List<RecipeTag> getCookingStyles() {
        return cookingStyles;
    }

    public void setCookingStyles(List<RecipeTag> cookingStyles) {
        this.cookingStyles = cookingStyles;
    }

    public List<RecipeComment> getComments() {
        return comments;
    }

    public void setComments(List<RecipeComment> comments) {
        this.comments = comments;
    }
}

