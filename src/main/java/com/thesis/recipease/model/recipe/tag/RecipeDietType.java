package com.thesis.recipease.model.recipe.tag;

public class RecipeDietType {
    private int recipeId;
    private String dietType;

    public RecipeDietType() {}
    public RecipeDietType(int recipeId, String dietType) {
        this.recipeId = recipeId;
        this.dietType = dietType;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    @Override
    public String toString() {
        return dietType;
    }
}
