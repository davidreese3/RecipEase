package com.thesis.recipease.model.recipe.tag;

public class RecipeCuisine {
    private int recipeId;
    private String cuisine;

    public RecipeCuisine() {}
    public RecipeCuisine(int recipeId, String cuisine) {
        this.recipeId = recipeId;
        this.cuisine = cuisine;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    @Override
    public String toString() {
        return cuisine;
    }
}

