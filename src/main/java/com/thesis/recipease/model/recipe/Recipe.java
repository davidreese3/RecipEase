package com.thesis.recipease.model.recipe;

import com.thesis.recipease.model.recipe.tag.*;
import com.thesis.recipease.model.web.recipe.tag.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Recipe {
    private RecipeInfo recipeInfo;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDirection> recipeDirections;
    private LinkedHashMap<String, String> recipeTags;

    public Recipe(){}

    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections, LinkedHashMap<String, String> recipeTags) {
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeDirections = recipeDirections;
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

    //    public List<RecipeTag> getRecipeHolidays() {
//        return recipeHolidays;
//    }
//
//    public void setRecipeHolidays(List<RecipeTag> recipeHolidays) {
//        this.recipeHolidays = recipeHolidays;
//    }
//
//    public List<RecipeTag> getRecipeMealTypes() {
//        return recipeMealTypes;
//    }
//
//    public void setRecipeMealTypes(List<RecipeTag> recipeMealTypes) {
//        this.recipeMealTypes = recipeMealTypes;
//    }
//
//    public List<RecipeTag> getRecipeCuisines() {
//        return recipeCuisines;
//    }
//
//    public void setRecipeCuisines(List<RecipeTag> recipeCuisines) {
//        this.recipeCuisines = recipeCuisines;
//    }
//
//    public List<RecipeTag> getRecipeAllergens() {
//        return recipeAllergens;
//    }
//
//    public void setRecipeAllergens(List<RecipeTag> recipeAllergens) {
//        this.recipeAllergens = recipeAllergens;
//    }
//
//    public List<RecipeTag> getRecipeDietTypes() {
//        return recipeDietTypes;
//    }
//
//    public void setRecipeDietTypes(List<RecipeTag> recipeDietTypes) {
//        this.recipeDietTypes = recipeDietTypes;
//    }
//
//    public List<RecipeTag> getRecipeCookingLevels() {
//        return recipeCookingLevels;
//    }
//
//    public void setRecipeCookingLevels(List<RecipeTag> recipeCookingLevels) {
//        this.recipeCookingLevels = recipeCookingLevels;
//    }

    /*
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", yield=" + yield +
                ", unitOfYield='" + unitOfYield + '\'' +
                ", prepMin=" + prepMin +
                ", prepHr=" + prepHr +
                ", processMin=" + processMin +
                ", processHr=" + processHr +
                ", totalMin=" + totalMin +
                ", totalHr=" + totalHr +
                '}';
    } */
}

