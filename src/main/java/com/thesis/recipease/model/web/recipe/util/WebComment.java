package com.thesis.recipease.model.web.recipe.util;

public class WebComment {
    private int recipeId;
    private String commentText;

    public WebComment(){}

    public WebComment(int recipeId, String commentText) {
        this.recipeId = recipeId;
        this.commentText = commentText;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
