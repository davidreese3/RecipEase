package com.thesis.recipease.model.recipe;

import com.thesis.recipease.model.web.recipe.WebDirection;
import com.thesis.recipease.model.web.recipe.WebIngredient;

import java.util.List;

public class Recipe {
    private RecipeInfo recipeInfo;
    private List<RecipeIngredient> recipeIngredients;
    private List<RecipeDirection> recipeDirections;

    public Recipe(){}
    public Recipe(RecipeInfo recipeInfo, List<RecipeIngredient> recipeIngredients, List<RecipeDirection> recipeDirections){
        this.recipeInfo = recipeInfo;
        this.recipeIngredients = recipeIngredients;
        this.recipeDirections = recipeDirections;
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

