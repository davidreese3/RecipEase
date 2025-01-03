package com.thesis.recipease.model.recipe;

import com.thesis.recipease.model.recipe.tag.*;
import com.thesis.recipease.model.web.recipe.tag.*;

import java.util.List;

public class Recipe {
    private RecipeInfo recipeInfo;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDirection> recipeDirections;
    private List<RecipeHoliday> recipeHolidays;
    private List<RecipeMealType> recipeMealTypes;
    private List<RecipeCuisine> recipeCuisines;
    private List<RecipeAllergen> recipeAllergens;
    private List<RecipeDietType> recipeDietTypes;
    private List<RecipeCookingLevel> recipeCookingLevels;

    public Recipe(){}

    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections, List<RecipeHoliday> recipeHolidays, List<RecipeMealType> recipeMealTypes, List<RecipeCuisine> recipeCuisines, List<RecipeAllergen> recipeAllergens, List<RecipeDietType> recipeDietTypes, List<RecipeCookingLevel> recipeCookingLevels) {
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeDirections = recipeDirections;
        this.recipeHolidays = recipeHolidays;
        this.recipeMealTypes = recipeMealTypes;
        this.recipeCuisines = recipeCuisines;
        this.recipeAllergens = recipeAllergens;
        this.recipeDietTypes = recipeDietTypes;
        this.recipeCookingLevels = recipeCookingLevels;
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

    public List<RecipeHoliday> getRecipeHolidays() {
        return recipeHolidays;
    }

    public void setRecipeHolidays(List<RecipeHoliday> recipeHolidays) {
        this.recipeHolidays = recipeHolidays;
    }

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

