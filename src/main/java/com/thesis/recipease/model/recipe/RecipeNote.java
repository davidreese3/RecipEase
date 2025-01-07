package com.thesis.recipease.model.recipe;

public class RecipeNote {
    private int recipeId;
    private String note;

    public RecipeNote(){}

    public RecipeNote(int recipeId, String note) {
        this.recipeId = recipeId;
        this.note = note;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
