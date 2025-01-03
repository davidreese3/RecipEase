package com.thesis.recipease.model.recipe.tag;

public class RecipeCookingLevel {
    private int recipeId;
    private String cookingLevel;

    public RecipeCookingLevel() {}
    public RecipeCookingLevel(int recipeId, String cookingLevel) {
        this.recipeId = recipeId;
        this.cookingLevel = cookingLevel;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getCookingLevel() {
        return cookingLevel;
    }

    public void setCookingLevel(String cookingLevel) {
        this.cookingLevel = cookingLevel;
    }

    @Override
    public String toString() {
        return cookingLevel;
    }
}
