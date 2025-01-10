package com.thesis.recipease.model.recipe.component;

public class RecipeDirection {
    private int recipeId;
    private int stepNum;
    private String direction;
    private String method;
    private int temp;
    private String level;

    public RecipeDirection(){}

    public RecipeDirection(int recipeId, int stepNum, String direction, String method, int temp, String level) {
        this.recipeId = recipeId;
        this.stepNum = stepNum;
        this.direction = direction;
        this.method = method;
        this.temp = temp;
        this.level = level;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "RecipeDirection{" +
                ", direction='" + direction + '\'' +
                ", method='" + method + '\'' +
                ", temp=" + temp +
                ", level='" + level + '\'' +
                '}';
    }
}
