package com.thesis.recipease.model.web.recipe.engagement;

public class WebComment {
    private String commentText;

    public WebComment(){}

    public WebComment(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
