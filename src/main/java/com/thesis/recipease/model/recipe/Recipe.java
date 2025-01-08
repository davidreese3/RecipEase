package com.thesis.recipease.model.recipe;

import java.util.LinkedHashMap;
import java.util.List;

public class Recipe {
    private RecipeInfo recipeInfo;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDirection> recipeDirections;
    private RecipeNote recipeNote;
    private List<RecipeLink> recipeLink;
    private LinkedHashMap<String, String> recipeTags;


    public Recipe(){}

    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections, RecipeNote recipeNote, List<RecipeLink> recipeLink, LinkedHashMap<String, String> recipeTags) {
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeDirections = recipeDirections;
        this.recipeNote = recipeNote;
        this.recipeLink = recipeLink;
        this.recipeTags = recipeTags;
    }

    //    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections, List<RecipeTag> recipeHolidays, List<RecipeTag> recipeMealTypes, List<RecipeTag> recipeCuisines, List<RecipeTag> recipeAllergens, List<RecipeTag> recipeDietTypes, List<RecipeTag> recipeCookingLevels) {
//        this.recipeInfo = recipeInfo;
//        this.recipeIngredients = recipeIngredients;
//        this.recipeDirections = recipeDirections;
//        this.recipeHolidays = recipeHolidays;
//        this.recipeMealTypes = recipeMealTypes;
//        this.recipeCuisines = recipeCuisines;
//        this.recipeAllergens = recipeAllergens;
//        this.recipeDietTypes = recipeDietTypes;
//        this.recipeCookingLevels = recipeCookingLevels;
//    }

    public RecipeInfo getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipeInfo(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<RecipeDirection> getRecipeDirections() {
        return recipeDirections;
    }

    public void setRecipeDirections(List<RecipeDirection> recipeDirections) {
        this.recipeDirections = recipeDirections;
    }

    public LinkedHashMap<String, String> getRecipeTags() {
        return recipeTags;
    }

    public void setRecipeTags(LinkedHashMap<String, String> recipeTags) {
        this.recipeTags = recipeTags;
    }

    public RecipeNote getRecipeNote() {
        return recipeNote;
    }

    public void setRecipeNote(RecipeNote recipeNote) {
        this.recipeNote = recipeNote;
    }

    public List<RecipeLink> getRecipeLink() {
        return recipeLink;
    }

    public void setRecipeLink(List<RecipeLink> recipeLink) {
        this.recipeLink = recipeLink;
    }
}

