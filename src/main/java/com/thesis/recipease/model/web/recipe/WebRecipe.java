package com.thesis.recipease.model.web.recipe;

import com.thesis.recipease.model.web.recipe.tag.*;

import java.util.List;

public class WebRecipe {
    private String name;
    private String description;
    private Double yield;
    private String unitOfYield;
    private Integer prepMin;
    private Integer prepHr;
    private Integer processMin;
    private Integer processHr;
    private int totalMin;
    private int totalHr;
    private List<WebIngredient> ingredients;
    private List<WebDirection> directions;
    private List<WebHoliday> holidays;
    private List<WebMealType> mealTypes;
    private List<WebCuisine> cuisines;
    private List<WebAllergen> allergens;
    private List<WebDietType> dietTypes;
    private List<WebCookingLevel> cookingLevels;



    public WebRecipe(){}

    public WebRecipe(String name, String description, Double yield, String unitOfYield, Integer prepMin, Integer prepHr, Integer processMin, Integer processHr, int totalMin, int totalHr, List<WebIngredient> ingredients, List<WebDirection> directions, List<WebHoliday> holidays, List<WebMealType> mealTypes, List<WebCuisine> cuisines, List<WebAllergen> allergens, List<WebDietType> dietTypes, List<WebCookingLevel> cookingLevels) {
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
        this.ingredients = ingredients;
        this.directions = directions;
        this.holidays = holidays;
        this.mealTypes = mealTypes;
        this.cuisines = cuisines;
        this.allergens = allergens;
        this.dietTypes = dietTypes;
        this.cookingLevels = cookingLevels;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public String getUnitOfYield() {
        return unitOfYield;
    }

    public void setUnitOfYield(String unitOfYield) {
        this.unitOfYield = unitOfYield;
    }

    public Integer getPrepMin() {
        return prepMin;
    }

    public void setPrepMin(Integer prepMin) {
        this.prepMin = prepMin;
    }

    public Integer getPrepHr() {
        return prepHr;
    }

    public void setPrepHr(Integer prepHr) {
        this.prepHr = prepHr;
    }

    public Integer getProcessMin() {
        return processMin;
    }

    public void setProcessMin(Integer processMin) {
        this.processMin = processMin;
    }

    public Integer getProcessHr() {
        return processHr;
    }

    public void setProcessHr(Integer processHr) {
        this.processHr = processHr;
    }

    public int getTotalMin() { return totalMin; }

    public void setTotalMin(int totalMin) { this.totalMin = totalMin; }

    public int getTotalHr() { return totalHr; }

    public void setTotalHr(int totalHr) { this.totalHr = totalHr; }

    public List<WebIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<WebIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<WebDirection> getDirections() {
        return directions;
    }

    public void setDirections(List<WebDirection> directions) {
        this.directions = directions;
    }

    public List<WebHoliday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<WebHoliday> holidays) {
        this.holidays = holidays;
    }

    public List<WebMealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<WebMealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public List<WebCuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<WebCuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public List<WebAllergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<WebAllergen> allergens) {
        this.allergens = allergens;
    }

    public List<WebDietType> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(List<WebDietType> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public List<WebCookingLevel> getCookingLevels() {
        return cookingLevels;
    }

    public void setCookingLevels(List<WebCookingLevel> cookingLevels) {
        this.cookingLevels = cookingLevels;
    }

    @Override
    public String toString() {
        StringBuilder ingredientInfo = new StringBuilder();
        if (ingredients != null && !ingredients.isEmpty()) {
            ingredientInfo.append("[");
            for (WebIngredient ingredient : ingredients) {
                ingredientInfo.append(ingredient.toString()).append(", ");
            }
            // Remove the trailing comma and space
            ingredientInfo.setLength(ingredientInfo.length() - 2);
            ingredientInfo.append("]");
        } else {
            ingredientInfo.append("[]");
        }

        StringBuilder directionInfo = new StringBuilder();
        if (directions != null && !directions.isEmpty()) {
            directionInfo.append("[");
            for (WebDirection direction : directions) {
                directionInfo.append(direction.toString()).append(", ");
            }
            // Remove the trailing comma and space
            directionInfo.setLength(directionInfo.length() - 2);
            directionInfo.append("]");
        } else {
            directionInfo.append("[]");
        }

        return "WebRecipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", yield=" + yield +
                ", unitOfYield='" + unitOfYield + '\'' +
                ", prepMin=" + prepMin +
                ", prepHr=" + prepHr +
                ", processMin=" + processMin +
                ", processHr=" + processHr +
                ", ingredients=" + ingredientInfo.toString() +
                ", directions=" + directionInfo.toString() +
                '}';
    }
}
