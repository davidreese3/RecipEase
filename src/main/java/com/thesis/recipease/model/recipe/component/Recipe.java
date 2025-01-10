package com.thesis.recipease.model.recipe.component;

import java.util.LinkedHashMap;
import java.util.List;

public class Recipe {
    private RecipeInfo recipeInfo;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDirection> recipeDirections;
    private RecipeNote recipeNote;
    private List<RecipeLink> recipeLinks;
    private List<RecipeUserSubstitutionEntry> userSubstitutionEntries;
    private RecipePhoto recipePhoto;
    private LinkedHashMap<String, String> recipeTags;


    public Recipe(){}

    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections, RecipeNote recipeNote, List<RecipeLink> recipeLinks, List<RecipeUserSubstitutionEntry> userSubstitutionEntries, RecipePhoto recipePhoto, LinkedHashMap<String, String> recipeTags) {
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeDirections = recipeDirections;
        this.recipeNote = recipeNote;
        this.recipeLinks = recipeLinks;
        this.userSubstitutionEntries = userSubstitutionEntries;
        this.recipePhoto = recipePhoto;
        this.recipeTags = recipeTags;
    }

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

    public RecipeNote getRecipeNote() {
        return recipeNote;
    }

    public void setRecipeNote(RecipeNote recipeNote) {
        this.recipeNote = recipeNote;
    }

    public List<RecipeLink> getRecipeLinks() {
        return recipeLinks;
    }

    public void setRecipeLinks(List<RecipeLink> recipeLinks) {
        this.recipeLinks = recipeLinks;
    }

    public List<RecipeUserSubstitutionEntry> getUserSubstitutionEntries() {
        return userSubstitutionEntries;
    }

    public void setUserSubstitutionEntries(List<RecipeUserSubstitutionEntry> userSubstitutionEntries) {
        this.userSubstitutionEntries = userSubstitutionEntries;
    }

    public RecipePhoto getRecipePhoto() {
        return recipePhoto;
    }

    public void setRecipePhoto(RecipePhoto recipePhoto) {
        this.recipePhoto = recipePhoto;
    }

    public LinkedHashMap<String, String> getRecipeTags() {
        return recipeTags;
    }

    public void setRecipeTags(LinkedHashMap<String, String> recipeTags) {
        this.recipeTags = recipeTags;
    }
}

