package com.thesis.recipease.model.recipe.tag;

public class RecipeMealType {
    private int recipeId;
    private String mealType;

    public RecipeMealType() {}
    public RecipeMealType(int recipeId, String mealType) {
        this.recipeId = recipeId;
        this.mealType = mealType;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    @Override
    public String toString() {
        return mealType;
    }
}
