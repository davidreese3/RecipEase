package com.thesis.recipease.model.domain.recipe;

public class RecipeInfo {
    private int recipeId;
    private int userId;
    private String name;
    private String description;
    private double yield;
    private String unitOfYield;
    private int prepMin;
    private int prepHr;
    private int processMin;
    private int processHr;
    private int totalMin;
    private int totalHr;

    public RecipeInfo(){}
    public RecipeInfo(int recipeId, int userId, String name, String description, double yield, String unitOfYield, int prepMin, int prepHr, int processMin, int processHr, int totalMin, int totalHr) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.yield = yield;
        this.unitOfYield = unitOfYield;
        this.prepMin = prepMin;
        this.prepHr = prepHr;
        this.processMin = processMin;
        this.processHr = processHr;
        this.totalMin = totalMin;
        this.totalHr = totalHr;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) { this.yield = yield; }

    public String getUnitOfYield() {
        return unitOfYield;
    }

    public void setUnitOfYield(String unitOfYield) {
        this.unitOfYield = unitOfYield;
    }

    public int getPrepMin() {
        return prepMin;
    }

    public void setPrepMin(int prepMin) {
        this.prepMin = prepMin;
    }

    public int getPrepHr() { return prepHr; }

    public void setPrepHr(int prepHr) {
        this.prepHr = prepHr;
    }

    public int getProcessMin() {
        return processMin;
    }

    public void setProcessMin(int processMin) {
        this.processMin = processMin;
    }

    public int getProcessHr() {
        return processHr;
    }

    public void setProcessHr(int processHr) {
        this.processHr = processHr;
    }

    public int getTotalMin() { return totalMin; }

    public void setTotalMin(int totalMin) { this.totalMin = totalMin; }

    public int getTotalHr() { return totalHr; }

    public void setTotalHr(int totalHr) { this.totalHr = totalHr; }

    @Override
    public String toString() {
        return "RecipeInfo{" +
                "recipeId=" + recipeId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", yield=" + yield +
                ", unitOfYield='" + unitOfYield + '\'' +
                ", prepMin=" + prepMin +
                ", prepHr=" + prepHr +
                ", processMin=" + processMin +
                ", processHr=" + processHr +
                ", totalMin=" + totalMin +
                ", totalHr=" + totalHr +
                '}';
    }
}
