package com.thesis.recipease.model.recipe;

public class RecipePhoto {
    private int recipeId;
    private String fileName;
    private String fileLocation;

    public RecipePhoto(){}

    public RecipePhoto(int recipeId, String fileName, String fileLocation) {
        this.recipeId = recipeId;
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
