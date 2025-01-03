package com.thesis.recipease.model.recipe.tag;

public class RecipeHoliday {
    private int recipeId;
    private String holiday;

    public RecipeHoliday() {}
    public RecipeHoliday(int recipeId, String holiday) {
        this.recipeId = recipeId;
        this.holiday = holiday;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    @Override
    public String toString() {
        return holiday;
    }
}
