package com.thesis.recipease.model.domain.recipe.util;

public class RecipeBookmark {
    private int userId;
    private int recipeId;

    public RecipeBookmark(){}

    public RecipeBookmark(int userId, int recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
