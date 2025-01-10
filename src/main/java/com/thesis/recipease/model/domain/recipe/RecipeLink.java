package com.thesis.recipease.model.domain.recipe;

public class RecipeLink {
    private int recipeId;
    private String link;

    public RecipeLink(){}
    public RecipeLink(int recipeId, String link){
        this.recipeId = recipeId;
        this.link = link;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
