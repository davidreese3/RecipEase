package com.thesis.recipease.model;

public class Profile {
    int id;
    String firstName;
    String lastName;
    String cookingLevel;
    String favoriteDish;
    String favoriteCuisine;

    public Profile(){}
    public Profile(int id, String firstName, String lastName, String cookingLevel, String favoriteDish, String favoriteCuisine) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cookingLevel = cookingLevel;
        this.favoriteDish = favoriteDish;
        this.favoriteCuisine = favoriteCuisine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCookingLevel() {
        return cookingLevel;
    }

    public void setCookingLevel(String cookingLevel) {
        this.cookingLevel = cookingLevel;
    }

    public String getFavoriteDish() {
        return favoriteDish;
    }

    public void setFavoriteDish(String favoriteDish) {
        this.favoriteDish = favoriteDish;
    }

    public String getFavoriteCuisine() {
        return favoriteCuisine;
    }

    public void setFavoriteCuisine(String favoriteCuisine) {
        this.favoriteCuisine = favoriteCuisine;
    }
}
