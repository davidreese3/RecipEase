package com.thesis.recipease.model.domain.recipe.util;

public class RecipeComment {
    private int recipeId;
    private int commentId;
    private int commentUserId;
    private String commentText;
    private String commentUserName;

    public RecipeComment(){}

    public RecipeComment(int recipeId, int commentId, int commentUserId, String commentText, String commentUserName) {
        this.recipeId = recipeId;
        this.commentId = commentId;
        this.commentUserId = commentUserId;
        this.commentText = commentText;
        this.commentUserName = commentUserName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }
}
