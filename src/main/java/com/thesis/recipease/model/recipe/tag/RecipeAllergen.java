package com.thesis.recipease.model.recipe.tag;

public class RecipeAllergen {
    private int recipeId;
    private String allergen;

    public RecipeAllergen() {}
    public RecipeAllergen(int recipeId, String allergen) {
        this.recipeId = recipeId;
        this.allergen = allergen;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    @Override
    public String toString() {
        return allergen;
    }
}

